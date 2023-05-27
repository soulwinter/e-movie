package com.emovie.service;

import com.emovie.dto.MovieDTO;
import com.emovie.dto.SearchParam;
import com.emovie.entity.Movie;
import com.emovie.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMovieService {


    Result detailInfo(int id);

//    Result listInfo(int requestPage,int movieNumberPerPage);

    Result listInfo(SearchParam param);

    Result searchRecommendation(String movieInfoString);


    Result getFilterItem();


    //TODO 根据用户的打分更新对其电影的推荐
    Result updateRecommend(int userId,int movieId);

    public List<Movie> getSimilarMovie(MovieDTO movie);
    //在首页给该用户推荐100个电影
    Result getUserRecommend(int userId, int pageSize, int offset);

    Result getMovieById(int id);
}
