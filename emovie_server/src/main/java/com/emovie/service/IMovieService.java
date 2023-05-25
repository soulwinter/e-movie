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
}
