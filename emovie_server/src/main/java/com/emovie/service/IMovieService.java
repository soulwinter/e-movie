package com.emovie.service;

import com.emovie.dto.SearchParam;
import com.emovie.util.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface IMovieService {


    Result detailInfo(int id);

//    Result listInfo(int requestPage,int movieNumberPerPage);

    Result listInfo(SearchParam param);

    Result searchRecommendation(String movieInfoString);


    Result getFilterItem();


    //TODO 根据用户的打分更新对其电影的推荐
    Result updateRecommend(int userId,int movieId);

    //TODO 在详情页推喜欢这部电影的人也喜欢的电影，用于推荐
    Result getSimilarMovie(int movie_id);

    //在首页给该用户推荐100个电影
    Result getUserRecommend(int userId, int pageSize, int offset);

}
