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
@Api(value = "管理员controller",tags = {"管理员接口"})
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Resource
    IMovieService movieService;


    /**
     *创建
     * @return
     */


    @ApiOperation(value = "新增电影信息", notes = "向网站新增一部电影的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "basic", value = "电影基本信息", required = true)
    })
    @PostMapping("/newMovie")
    public Result newMovie(@RequestBody Movie basic) throws IOException {return movieService.newMovie(basic);}


    /**
     * 更新电影Basic信息的接口
     * @param basic
     * @return
     */


    @ApiOperation(value = "更新电影信息", notes = "更新电影的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "basic", value = "电影基础信息", required = true)
    })
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


    @ApiOperation(value = "新增KeyWord接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id ", value = "电影id", required = true),
            @ApiImplicitParam(name = "KeyWord", value = "新增的keyword的字符串", required = true)
    })
    @GetMapping("/addKeyWord")
    public Result addKeyWord(@RequestParam String KeyWord,@RequestParam int id){ return movieService.addKeyWord(KeyWord,id);}


    /**
     * 电影KeyWord的删除接口
     * @param KeyWord
     * @param id
     * @return
     */


    @ApiOperation(value = "删除KeyWord接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id ", value = "电影id", required = true),
            @ApiImplicitParam(name = "KeyWord", value = "删除的keyword的字符串", required = true)
    })
    @GetMapping("/deleteKeyWord")
    public Result deleteKeyWord(@RequestParam String KeyWord,@RequestParam int id){ return movieService.deleteKeyWord(KeyWord,id);}

    /**
     * 电影Genre的新增接口
     * @param Genre
     * @param id
     * @return
     */


    @ApiOperation(value = "新增Genre接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id ", value = "电影id", required = true),
            @ApiImplicitParam(name = "Genre", value = "新增的Genre的字符串", required = true)
    })
    @GetMapping("/addGenre")
    public Result addGenre(@RequestParam String Genre,@RequestParam int id) throws IOException { return movieService.addGenre(Genre,id);}


    /**
     * 电影Genre的删除接口
     * @param Genre
     * @param id
     * @return
     */


    @ApiOperation(value = "删除Genre接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id ", value = "电影id", required = true),
            @ApiImplicitParam(name = "Genre", value = "删除的Genre的字符串", required = true)
    })
    @GetMapping("/deleteGenre")
    public Result deleteGenre(@RequestParam String Genre,@RequestParam int id) throws IOException { return movieService.deleteGenre(Genre,id);}
}
