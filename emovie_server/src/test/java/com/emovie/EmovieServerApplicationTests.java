package com.emovie;

import com.emovie.entity.Movie;
import com.emovie.service.IMovieService;
import com.emovie.service.IUserService;
import com.emovie.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class EmovieServerApplicationTests {


//    @Qualifier("IMovieService")
    @Resource
    IMovieService movieService;


    @Test
    void contextLoads() {

    }

    @Test
    void testRecommendation(){
        Result result1=movieService.detailInfo(110);
        System.out.println(result1);
    }

}
