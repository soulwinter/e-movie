package com.emovie.service;

import com.emovie.util.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface IMovieService {


    Result detailInfo(int id);

    Result listInfo(int requestPage,int movieNumberPerPage);


    /** @Description: 以下代码为陈航所写
     * @Author: 陈航
     * @Date: 2023/5/26 14:44
     */

    //TODO 根据用户的打分更新对其电影的推荐
    Result updateRecommend(int userId,int movieId);

    //TODO 在详情页推喜欢这部电影的人也喜欢的电影，用于推荐
    Result getSimilarMovie(int movie_id);

    //在首页给该用户推荐100个电影
    Result getUserRecommend(int userId, int pageSize, int offset);
}
