package com.emovie.controller;

import com.emovie.dto.SearchParam;
import com.emovie.entity.Movie;
import com.emovie.service.IMovieService;
import com.emovie.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;


@Slf4j
@Api(value = "电影controller",tags = {"电影接口"})
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Resource
    IMovieService movieService;


    /**
     *创建
     * @return
     */
    @PostMapping("/newMovie")
    public Result newMovie(@RequestBody Movie basic) throws IOException {return movieService.newMovie(basic);}


    /**
     * 更新电影Basic信息的接口
     * @param basic
     * @return
     */
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody Movie basic) throws IOException {
        return movieService.updateInfo(basic);
    }


    /**
     * 电影KeyWord的新增接口
     * @param KeyWord
     * @param id
     * @return
     */
    @GetMapping("/addKeyWord")
    public Result addKeyWord(@RequestParam String KeyWord,@RequestParam int id){ return movieService.addKeyWord(KeyWord,id);}


    /**
     * 电影KeyWord的删除接口
     * @param KeyWord
     * @param id
     * @return
     */
    @GetMapping("/deleteKeyWord")
    public Result deleteKeyWord(@RequestParam String KeyWord,@RequestParam int id){ return movieService.deleteKeyWord(KeyWord,id);}

    /**
     * 电影Genre的新增接口
     * @param Genre
     * @param id
     * @return
     */
    @GetMapping("/addGenre")
    public Result addGenre(@RequestParam String Genre,@RequestParam int id) throws IOException { return movieService.addGenre(Genre,id);}


    /**
     * 电影Genre的删除接口
     * @param Genre
     * @param id
     * @return
     */
    @GetMapping("/deleteGenre")
    public Result deleteGenre(@RequestParam String Genre,@RequestParam int id) throws IOException { return movieService.deleteGenre(Genre,id);}
}
