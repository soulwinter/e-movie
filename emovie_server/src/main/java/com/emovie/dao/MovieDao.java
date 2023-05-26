package com.emovie.dao;

import com.emovie.entity.Movie;
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

}
