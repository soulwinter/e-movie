package com.emovie.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.emovie.dao.GenreDao;
import com.emovie.dao.MovieDao;
import com.emovie.dto.MovieDTO;
import com.emovie.dto.MovieDocument;
import com.emovie.dto.SearchParam;
import com.emovie.entity.Movie;
import com.emovie.service.IMovieService;
import com.emovie.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.emovie.util.Constants.ES_INDEX;


@Service
@Slf4j
public class MovieServiceImpl implements IMovieService {

    @Autowired
    MovieDao movieDao;

    @Autowired
//    @Qualifier("restHighLevelClient")
    RestHighLevelClient restHighLevelClient;

    @Autowired
    GenreDao genreDao;


    @Override
    public Result detailInfo(int id) {
        Result result=null;
        try {

            //通过id去数据库分开查找信息
            MovieDTO movie_info = new MovieDTO();
            movie_info.setBasic(movieDao.getBasic(id));
            movie_info.setCountry(movieDao.getCountry(id));
            movie_info.setGenre(movieDao.getGenre(id));
            movie_info.setId((long) id);
            movie_info.setKeyword(movieDao.getKeyword(id));
//            movie_info.setVote(movieDao.getVote(id));
            List<Movie> similar = getSimilarMovie(movie_info);
            System.out.println("similar: "+similar);
            movie_info.setSimilarMovies(similar);
            result = Result.ok(movie_info);
        } catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {

            return result;
        }


    }

    @Override
    public Result listInfo(SearchParam param) {
        Result result=null;

        try {
            //1.准备request
            SearchRequest request = new SearchRequest(ES_INDEX);

            //2.准备DSL
            //2.1query
            builderBasicQuery(param,request);

            //2.2分页
            int page= param.getRequestPage();
            int size= param.getMovieNumberPerPage();
            request.source().from((page-1)*size).size(size);
            //3.发送请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            System.out.println(response);
            //4.解析响应
            result=handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            return result;
        }

    }

    private static void builderBasicQuery(SearchParam param, SearchRequest request) {
        //构建BooleanQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //关键词搜索
        if(StrUtil.isNotBlank(param.getMovieInfoString())){
            boolQuery.must(QueryBuilders.matchQuery("title", param.getMovieInfoString()));
        }else{
            boolQuery.must(QueryBuilders.matchAllQuery());
        }
        //条件过滤
        //是否成人级
        if(param.getAdult()!=null){
            boolQuery.filter(QueryBuilders.termQuery("adult", param.getAdult()));
        }

        //年份过滤
        if(StrUtil.isNotBlank(param.getReleaseDate())){
            boolQuery.filter(QueryBuilders.termQuery("releaseDate", param.getReleaseDate()));
        }
        //得分过滤
        if(param.getVoteAverageFrom()!=null&& param.getVoteAverageTo()!=null){
            boolQuery.filter(QueryBuilders.rangeQuery("voteAverage")
                    .gte(param.getVoteAverageFrom())
                    .lte(param.getVoteAverageTo()));
        }
        //类型过滤
        if(param.getGenreList()!=null&&!param.getGenreList().isEmpty()){
            boolQuery.must(QueryBuilders.matchQuery("genreList",param.getGenreList().toString()));
        }
        request.source().query(boolQuery);
    }


    /**
     * 获取所有筛选项
     * @return
     */
    @Override
    public Result getFilterItem() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("adult",new boolean[]{true,false});
        map.put("releaseDate",new String[]{"1931","2016"});
        map.put("voteAverage",new int[]{0,10});

        map.put("genre",genreDao.getAllName());
        return Result.ok(map);
    }

    /**
     * 搜索时推荐
     * @param movieInfoString
     * @return
     */
    @Override
    public Result searchRecommendation(String movieInfoString) {
        Result result=null;
        try {
            //1.准备request
            SearchRequest request = new SearchRequest(ES_INDEX);

            //2.准备DSL
            //2.1.query
            if(StrUtil.isNotBlank(movieInfoString)){
                request.source().query(QueryBuilders.matchQuery("title", movieInfoString));
            }else{
                request.source().query(QueryBuilders.matchAllQuery());
            }
            //2.2.分页
            request.source().from(0).size(10);
            //3.发送请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            System.out.println(response);
            //4.解析响应
            SearchHit[] hits = response.getHits().getHits();
            ArrayList<Movie> movies = new ArrayList<>();
            for (SearchHit hit : hits) {
                MovieDocument movieDocument = JSONUtil.toBean(hit.getSourceAsString(), MovieDocument.class);
                Movie movie = new Movie();
                movie.setId(movieDocument.getId());
                movie.setTitle(movieDocument.getTitle());
                movies.add(movie);
            }
            result=Result.ok(movies,response.getHits().getTotalHits().value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            return result;
        }
    }

    private Result handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        //4.1获取总条数
        long total = searchHits.getTotalHits().value;
        log.info("总条数："+total);
        //4.2文档数组
        SearchHit[] hits = searchHits.getHits();
        //4.3遍历获得id
        ArrayList<Double> ids = new ArrayList<>();
        for (SearchHit hit : hits) {
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            MovieDocument movieDocument = JSONUtil.toBean(json, MovieDocument.class);
            System.out.println(movieDocument);
            //获取id装入ids中
            ids.add(movieDocument.getId());
            //获取高亮结果

            //
        }
        if (ids.isEmpty())
        {
            return Result.ok(null);
        }
        //通过ids查找数据库数据
        String json = JSONUtil.toJsonStr(ids);
        List<Movie> result = movieDao.getByIds(json.substring(1, json.length() - 1));
//        List<Movie> result = movieDao.getByIds2(ids);

        //返回list
        return Result.ok(result,total);
    }


    @Override
    //TODO 根据用户的打分更新对其电影的推荐
    public Result updateRecommend(int userId,int movieId){
        Result result=null;
        return result;
    }


    /**
     * 喜欢这部电影的人也喜欢的电影，用于推荐
     * @param movie 电影
     * @return 电影集合
     */
    @Override
    public List<Movie> getSimilarMovie(MovieDTO movie){
        List<Movie> result=null;
        try{
            List<Integer> similarIds=movieDao.getSimilarMovie(movie.getId());
            //这里想做与基于内容的结合，与es的结果进行融合得到一个最终的结果，对于不在rating中的电影，直接返回es搜索的结果;
            if(similarIds.isEmpty()){
                SearchParam param = new SearchParam();
                param.setMovieInfoString(movie.getBasic().getTitle());
                param.setAdult(movie.getBasic().getAdult()==1);
                param.setGenreList(movie.getGenre());
                param.setMovieNumberPerPage(25);//推荐25个
                param.setRequestPage(1);
                result= (List<Movie>) listInfo(param).getData();
            }else{

                result = movieDao.getMovieByIDList(similarIds);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    //在首页给该用户推荐100个电影
    public Result getUserRecommend(int userId, int pageSize, int offset){
        Result result=null;
        try{
            List<Movie>data=movieDao.getUserRecommend(userId,pageSize,offset);
            result=Result.ok(data);
        }catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {
            return result;
        }
    }

    @Override
    //通过id获得当前电影的简介信息
    public Result getMovieById(int id) {
        Movie movie = movieDao.getBasic(id);
        return Result.ok(movie);
    }
}
