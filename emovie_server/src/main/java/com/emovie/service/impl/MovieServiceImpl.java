package com.emovie.service.impl;

import com.emovie.dao.MovieDao;
import com.emovie.dto.MovieDTO;
import com.emovie.entity.Movie;
import com.emovie.entity.User;
import com.emovie.service.IMovieService;
import com.emovie.util.MD5;
import com.emovie.util.RegexUtils;
import com.emovie.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;



@Service
@Slf4j
public class MovieServiceImpl implements IMovieService {

    @Autowired
    MovieDao movieDao;


    @Override
    public Result detailInfo(int id) {
        Result result=null;
        try {

            //通过id去数据库分开查找信息
            MovieDTO movie_info = new MovieDTO();
            movie_info.setBasic(movieDao.getBasic(id));
            movie_info.setCountry(movieDao.getCountry(id));
            movie_info.setGenre(movieDao.getGenre(id));
            movie_info.setId(new Long(id));
            movie_info.setKeyword(movieDao.getKeyword(id));
            movie_info.setVote(movieDao.getVote(id));


            result = Result.ok(movie_info);
        } catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {

            return result;
        }


    }

    @Override
    public Result listInfo(int requestPage,int movieNumberPerPage) {
        Result result=null;
        try {

            List<Movie> movies = movieDao.getLimit((requestPage - 1) * movieNumberPerPage,movieNumberPerPage);
            System.out.println(movies);
            result = Result.ok(movies);
        } catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {

            return result;
        }
    }

}
