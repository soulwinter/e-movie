package com.emovie;

import com.emovie.entity.Movie;
import com.emovie.service.IMovieService;
import com.emovie.service.IUserService;
import com.emovie.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EmovieServerApplicationTests {


    @Autowired
    public IMovieService iMovieService;

    @Test
    void contextLoads() {

    }

    @Test
    void testRecommendation(){
        Result result1=iMovieService.getSimilarMovie(110);
        Result result2=iMovieService.getUserRecommend(1,1,0);
    }

}
