package com.emovie.dao;

import com.emovie.entity.Movie;
import com.emovie.entity.MovieGenre;
import com.emovie.entity.MovieKeyword;
import com.emovie.entity.Vote;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;


@Mapper
public interface MovieDao {
//    查电影基本信息 传参名 movie_id
    @Select("select * from movie where id=#{movie_id}")
    @ResultMap(value = {"MovieresultMap"})
    public Movie getBasic(int movie_id);
//    查电影属于的类型 movie_id
    @Select("select name as genre from movie_genre,genre where movie_genre.id=#{movie_id} and movie_genre.Gen_id=genre.id")
    public List<String> getGenre(int movie_id);
//    查电影属于的公司 传参名 movie_id
    @Select("select name as company from company,movie_company where movie_company.id=#{movie_id} and movie_company.Com_id=company.id")
    public List<String> getCompany(int movie_id);
//    查电影属于的国家 传参名 movie_id
    @Select("select name as country from country,movie_country where movie_country.id=#{movie_id} and movie_country.Cou_id=country.id")
    public List<String> getCountry(int movie_id);
//    查询用户评分记录 传参名 movie_id
    @Select("select user_id,rating,timestamp from vote where movie_id=#{movie_id}")
    public List<Vote> getVote(int movie_id);
//    查询电影关键词 传参名 movie_id
    @Select("select name as keyword from keyword,movie_keyword where movie_keyword.id=#{movie_id} and movie_keyword.Key_id=keyword.id")
    public List<String> getKeyword(int movie_id);
    //    查询所有电影的信息
    @Select("select * from movie")
    @ResultMap(value = {"MovieresultMap"})
    public List<Movie> getAll();

    //    查询从n开始的m条电影数据 传参名 movie_id
    @Select("select * from movie limit #{n},#{m}")
    @ResultMap(value = {"MovieresultMap"})
    public List<Movie> getLimit (@Param("n")int n,@Param("m")int m);

    @Select("select * from movie where id in(${listJson})")
    @ResultMap(value = {"MovieresultMap"})
    public List<Movie> getByIds(String listJson);

    @Select("select * from movie where id in(${list})")
    @Results(id="MovieresultMap",value = {
            @Result(column="vote_average",property = "voteAverage"),
            @Result(column="vote_count",property = "voteCount"),
            @Result(column="poster_path",property = "posterPath"),
            @Result(column="original_title",property = "originalTitle"),
            @Result(column="original_language",property = "originalLanguage"),
            @Result(column="release_date",property = "releaseDate"),
    })
    public List<Movie> getByIds2(ArrayList<Double> list);

    /** @Description: 以下代码为陈航所写
     * @Author: 陈航
     * @Date: 2023/5/23 21:27
     */
    //查找某用户过去的5条评分记录
    @Select("select * from vote where user_id=#{user_id} order by `timestamp` desc LIMIT 5;")
    public List<Vote> getUserVote (int user_id);

    //查找喜欢这部电影的人也喜欢的电影，用于推荐，最多25个
    @Select("SELECT similarId from movie_similar_svd where movieid=#{movie_id} ORDER BY similardegree DESC; ")
    public List<Integer> getSimilarMovie(@Param("movie_id") long movie_id);

    //查找推荐给用户的电影,这是分页的，总共有每个用户推100个
    public List<Movie> getUserRecommend(@Param("userId") int userId,@Param("pageSize")int pageSize, @Param("offset")int offset);

    //根据id获得电影
    public List<Movie> getMovieByIDList(@Param("idList") List<Integer> idList);

    /**
     *@Author: Yu
     *@Date: 2023/5/31 17:31
     */
    /**
     * 关于Keyword的
     * */
    //传入Keyword的字符串，去匹配数据库中该电影是否有该Keyword tag 用于处理是否能够新增或者删除请求的Keyword
    @Select("SELECT * FROM movie_keyword where id=#{movie_id} AND Key_id=(SELECT id FROM keyword WHERE name=#{KeyWord});")
    public List<MovieKeyword> getKeywordByName(@Param("movie_id") int movie_id, @Param("KeyWord") String KeyWord);

    //删除电影的该Keyword
    @Delete("Delete FROM movie_keyword where id=#{movie_id} AND Key_id=(SELECT id FROM keyword WHERE name=#{KeyWord});")
    public int deleteKeywordByName(@Param("movie_id") int movie_id, @Param("KeyWord") String KeyWord);

    //插入一个种类Genre-movie键值对
    @Insert("insert into movie_keyword VALUES (#{movie_id},(SELECT id from keyword where name=#{KeyWord}),null)")
    public int addKeyWord(@Param("movie_id")int movieId,  @Param("KeyWord")String KeyWord);

    //查找是否有Genre
    @Select("SELECT COUNT(*) from keyword where name=#{KeyWord}")
    public int hasKeyWord(@Param("KeyWord") String KeyWord);

    //插入一个新的种类
    @Insert("insert into keyword VALUES (null,#{KeyWord},null)")
    public int addNewKeyWord(@Param("KeyWord")String KeyWord);



    /**
     * 关于Genre的
     * */
    //传入Genre的字符串，去匹配数据库中该电影是否有该Genre用于处理是否能够新增或者删除请求的Genre
    @Select("SELECT * FROM movie_genre where id=#{movie_id} AND Gen_id=(SELECT id FROM genre WHERE name=#{Genre});")
    public List<MovieGenre> getGenreByName(@Param("movie_id") int movie_id, @Param("Genre") String Genre);

    //删除电影的该Genre
    @Delete("Delete FROM movie_genre where id=#{movie_id} AND Gen_id=(SELECT id FROM keyword WHERE name=#{Genre});")
    public int deleteGenreByName(@Param("movie_id") int movie_id, @Param("Genre") String Genre);

    //插入一个种类Genre-movie键值对
    @Insert("insert into movie_genre VALUES (#{movie_id},(SELECT id from genre where name=#{Genre}),null)")
    public int addGenre(@Param("movie_id")int movieId,  @Param("Genre")String genre);

    //查找是否有Genre
    @Select("SELECT COUNT(*) from genre where name=#{Genre}")
    public int hasGenre(@Param("Genre") String Genre);

    //插入一个新的种类
    @Insert("insert into genre VALUES (null,#{Genre},null)")
    public int addNewGenre(@Param("Genre")String genre);
}
