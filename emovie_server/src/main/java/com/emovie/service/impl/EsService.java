package com.emovie.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.emovie.dao.MovieDao;
import com.emovie.dto.MovieDocument;
import com.emovie.entity.Movie;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EsService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    MovieDao movieDao;

    /**
     * 更新es中的Genre
     * @param movieId
     * @throws IOException
     */
    public void UpdateEsGenre(int movieId) throws IOException {
        List<String> list = movieDao.getGenre(movieId);
        System.out.println(list);
        UpdateRequest request = new UpdateRequest("movie", String.valueOf((double)movieId));

        request.doc("genreList",list);

        restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 新增电影+修改电影信息(覆盖旧的)
     * @param movie
     * @throws IOException
     */
    public void InsertEsMovie(Movie movie) throws IOException {

        List<String> genreList = movieDao.getGenre((int) (double) movie.getId());
        String releaseDate=null;

        if(StrUtil.isNotBlank(movie.getReleaseDate()))releaseDate = movie.getReleaseDate().substring(0, 4);
        else releaseDate="unknown";
//        System.out.println(genreList);
        MovieDocument movieDocument = new MovieDocument(
                movie.getId(),
                movie.getAdult() == 1,
                movie.getTitle(),
                movie.getPopularity(),
                releaseDate,
                movie.getVoteAverage(),
                genreList);

        IndexRequest request = new IndexRequest("movie").id(movie.getId().toString());
        request.source(JSONUtil.toJsonStr(movieDocument), XContentType.JSON);
        restHighLevelClient.index(request,RequestOptions.DEFAULT);
    }

//    /**
//     * 修改电影信息
//     * @param movie
//     * @throws IOException
//     */
//    public void updateEsMovie(Movie movie) throws IOException {
////        Movie movie = movieDao.getBasic(3);
//        String releaseDate;
//        if(StrUtil.isNotBlank(movie.getReleaseDate()))releaseDate = movie.getReleaseDate().substring(0, 4);
//        else releaseDate="unknown";
//        System.out.println(movie);
//        UpdateRequest request = new UpdateRequest("movie", movie.getId().toString());
//
//        request.doc(
//                "releaseDate",releaseDate,
//                );
//
//        restHighLevelClient.update(request,RequestOptions.DEFAULT);
//    }
}
