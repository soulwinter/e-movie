package com.emovie.controller;

import com.emovie.dto.SearchParam;
import com.emovie.entity.Movie;
import com.emovie.service.IMovieService;
import com.emovie.service.IUserService;
import com.emovie.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
@Api(value = "电影controller",tags = {"电影接口"})
@RestController
@RequestMapping("/movie")
public class MovieController {



    @Resource
    IMovieService movieService;


    @ApiOperation(value = "搜索电影", notes = "根据条件搜索电影信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movieInfoString", value = "搜索框中的字符串", required = true, dataType = "String"),
            @ApiImplicitParam(name = "requestPage", value = "请求的页码，要求返回该页码的全部电影信息", required = true, dataType = "int"),
            @ApiImplicitParam(name = "movieNumberPerPage", value = "每页电影数", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "adult", value = "是否是成人级别", required = false, dataType = "int"),
            @ApiImplicitParam(name = "originalLangage", value = "官方语言", required = false, dataType = "String"),
            @ApiImplicitParam(name = "releaseDate", value = "发布年份", required = false, dataType = "String"),
            @ApiImplicitParam(name = "voteAverageFrom", value = "评分起始", required = false, dataType = "int"),
            @ApiImplicitParam(name = "voteAverageTo", value = "评分结束", required = false, dataType = "int"),
            @ApiImplicitParam(name = "genre", value = "电影类型", required = false, dataType = "object")
    })
    @PostMapping("/listInfo")
    public Result listInfo(@RequestBody SearchParam param){
        System.out.println(param);
        return movieService.listInfo(param);
    }


    //说明是什么方法(可以理解为方法注释)
    @ApiOperation("获得电影详请")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "电影id",required=true)})
    @GetMapping("/detailInfo/{id}")
    public Result detailInfo(@PathVariable int id){
        return movieService.detailInfo(id);
    }

    /**
     * 搜索时推荐
     * @param movieInfoString
     * @return
     */


    @ApiOperation(value = "搜索时推荐电影", notes = "根据搜索框中内容进行推荐搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movieInfoString", value = "搜索框中的字符串", required = true)
    })
    @PostMapping("/searchRecommendation")
    public Result searchRecommendation(@RequestBody String movieInfoString){
        System.out.println(movieInfoString);
        return movieService.searchRecommendation(movieInfoString);
    }

    /**
     * 筛选的所有选项
     * @return
     */


    @ApiOperation(value = "获取筛选类型列表", notes = "获取电影推荐系统的筛选类型列表")
    @GetMapping("/filterItem")
    public Result getFilterItem(){
        return movieService.getFilterItem();
    }

    /**
     * 点击搜索时推荐的电影返回电影简介
     * @param id
     * @return
     */

    @ApiOperation(value = "根据 ID 获取电影信息", notes = "根据电影的 ID 获取电影的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "电影id", required = true)
    })
    @GetMapping("/{id}")
    public Result getMovieById(@PathVariable double id){
        return movieService.getMovieById((int)id);
    }

}
