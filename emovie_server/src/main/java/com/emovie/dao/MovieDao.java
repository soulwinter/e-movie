package com.emovie.dao;

import com.emovie.entity.Movie;
import com.emovie.entity.Vote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;


@Mapper
public interface MovieDao {
//    查电影基本信息 传参名 movie_id
    @Select("select * from movie where id=#{movie_id}")
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
    public List<Movie> getAll();
    //    查询电影总数
    @Select("select count(*) from movie")
    public int getSum();
    //    查询从n开始的m条电影数据 传参名 movie_id
    @Select("select * from movie limit #{n},#{m}")
    public List<Movie> getLimit (@Param("n")int n,@Param("m")int m);




    /** @Description: 以下代码为陈航所写
     * @Author: 陈航
     * @Date: 2023/5/23 21:27
     */
    //查找某用户过去的5条评分记录
    @Select("select * from vote where user_id=#{user_id} order by `timestamp` desc LIMIT 5;")
    public List<Vote> getUserVote (int user_id);

    //查找喜欢这部电影的人也喜欢的电影，用于推荐，最多25个
    @Select("SELECT similarId from movie_similar_svd where movieid=#{movie_id} ORDER BY similardegree DESC; ")
    public List<Integer> getSimilarMovie(@Param("movie_id") int movie_id);

    //查找推荐给用户的电影,这是分页的，总共有每个用户推100个
    public List<Movie> getUserRecommend(@Param("userId") int userId,@Param("pageSize")int pageSize, @Param("offset")int offset);

    //根据id获得电影
    public List<Movie> getMovieByIDList(@Param("idList") List<Integer> idList);
}
