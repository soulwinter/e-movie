package com.emovie;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.emovie.dao.MovieDao;
import com.emovie.dto.MovieDocument;
import com.emovie.entity.Movie;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ElasticTests {

    @Autowired
    RestHighLevelClient client;
    String MAPPING="{\n" +
            "  \"mappings\":{\n" +
            "    \"properties\":{\n" +
            "      \"id\":{\n" +
            "        \"type\":\"keyword\"\n" +
            "      },\n" +
            "      \"adult\":{\n" +
            "        \"type\":\"boolean\"\n" +
            "      },\n" +
            "      \"originalLanguage\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"title\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"popularity\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"releaseDate\":{\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"voteAverage\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    //新建索引库
    @Test
    public void createUserIndex() throws IOException {

        CreateIndexRequest request = new CreateIndexRequest("movie2");
        request.source(MAPPING, XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 1)
//                .put("index.number_of_replicas", 0)
//        );
    }

    //查询索引库
    @Test
    public void searchIndex() throws IOException {
        //1.创建Request语句
        GetIndexRequest request = new GetIndexRequest("movie");

        //3发送请求
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
    }

    @Autowired
    MovieDao movieDao;

    //批量导入
    @Test
    public void bulkTest() throws IOException {
        //1.创建Request语句
        BulkRequest request = new BulkRequest();
        //2.准备参数
        List<Movie> movieList = movieDao.getAll();
        System.out.println(movieList.size()+"=================");
        for (Movie movie : movieList) {
            List<String> genreList = movieDao.getGenre((int) (double) movie.getId());
            String releaseDate=null;
            if(StrUtil.isNotBlank(movie.getReleaseDate()))releaseDate = movie.getReleaseDate().substring(0, 4);
            else releaseDate="unknown";
            MovieDocument movieDocument = new MovieDocument(
                    movie.getId(),
                    movie.getAdult() == 1,
                    movie.getTitle(),
                    movie.getPopularity(),
                    releaseDate,
                    movie.getVoteAverage(),
                    genreList);

            request.add(new IndexRequest("movie")
                    .id(movie.getId().toString())
                    .source(JSONUtil.toJsonStr(movieDocument),XContentType.JSON));
        }


        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);

    }

//    @BeforeEach
//    void setUp(){
//        this.client=new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://8.130.92.141:9200")
//        ));
//    }
//
//    @AfterEach
//    void close() throws IOException {
//        this.client.close();
//    }


    @Test
    void testInsertOne() throws IOException {
        Movie movie = movieDao.getBasic(862);
        List<String> genreList = movieDao.getGenre((int) (double) movie.getId());
        String releaseDate=null;

        if(StrUtil.isNotBlank(movie.getReleaseDate()))releaseDate = movie.getReleaseDate().substring(0, 4);
        else releaseDate="unknown";
        System.out.println(genreList);
        MovieDocument movieDocument = new MovieDocument(
                movie.getId(),
                movie.getAdult() == 1,
                movie.getTitle(),
                movie.getPopularity(),
                releaseDate,
                movie.getVoteAverage(),
                genreList);

        IndexRequest request = new IndexRequest("movie").id(movie.getId().toString());
        request.source(JSONUtil.toJsonStr(movieDocument),XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }

    @Test
    void testSearchOne() throws IOException {
        GetRequest request = new GetRequest("movie", "3.0");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
    }

    @Test
    void testUpdateOne() throws IOException {

//        List<Movie> movieList = movieDao.getAll();
        Movie movie = movieDao.getBasic(3);
        String releaseDate;
        if(StrUtil.isNotBlank(movie.getReleaseDate()))releaseDate = movie.getReleaseDate().substring(0, 4);
        else releaseDate="unknown";
System.out.println(movie);
        UpdateRequest request = new UpdateRequest("movie", movie.getId().toString());


        request.doc("releaseDate",releaseDate);


        client.update(request,RequestOptions.DEFAULT);
    }

    /**
     * 更新es中年份字段
     * @throws IOException
     */
    @Test
    void testUpdateBulk() throws IOException {
        BulkRequest request = new BulkRequest();

        List<Movie> movieList = movieDao.getAll();
        System.out.println("================="+movieList.size()+"=================");
        for (Movie movie : movieList) {
            System.out.println(movie.getReleaseDate());
            //更新年份
            String releaseDate;
            if(StrUtil.isNotBlank(movie.getReleaseDate()))releaseDate = movie.getReleaseDate().substring(0, 4);
            else releaseDate="unknown";

            request.add(new UpdateRequest("movie",movie.getId().toString())
                    .doc("releaseDate",releaseDate));
        }
        client.bulk(request,RequestOptions.DEFAULT);
    }


    /**
     * 更新es中评分字段
     * @throws IOException
     */
    @Test
    void testUpdateBulk2() throws IOException {
        BulkRequest request = new BulkRequest();

        List<Movie> movieList = movieDao.getAll();
        System.out.println("================="+movieList.size()+"=================");
        for (Movie movie : movieList) {
            //更新评分
            request.add(new UpdateRequest("movie",movie.getId().toString())
                    .doc("voteAverage",movie.getVoteAverage()));
        }
        client.bulk(request,RequestOptions.DEFAULT);
    }


    @Test
    void testUpdateGenre() throws IOException {


        Movie movie = movieDao.getBasic(3);

        UpdateRequest request = new UpdateRequest("movie", movie.getId().toString());
        ArrayList<String> list = new ArrayList<>();
        list.add("Drama");
        list.add("Comedy");

        request.doc("genreList",list);


        client.update(request,RequestOptions.DEFAULT);
    }


}
