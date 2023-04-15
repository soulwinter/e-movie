package com.emovie.controller;

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


@Slf4j
@Api(value = "电影controller",tags = {"电影接口"})
@RestController
@RequestMapping("/movie")
public class MovieController {



    @Resource
    IMovieService movieService;


    //说明是什么方法(可以理解为方法注释)
    @ApiOperation("使用密码登录")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParams({@ApiImplicitParam(name = "telephone", value = "手机号",required=false),
            @ApiImplicitParam(name = "password", value = "密码",required=false)})
    @GetMapping("/baseInfo")
    public Result UserServiceImpl(){
        return null;
    }


    //说明是什么方法(可以理解为方法注释)
    @ApiOperation("获得电影详请")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "电影id",required=true)})
    @GetMapping("/detailInfo/{id}")
    public Result detailInfo(@PathVariable int id){

        return movieService.detailInfo(id);
    }

    //说明是什么方法(可以理解为方法注释)
    @ApiOperation("获得分页电影信息")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParams({@ApiImplicitParam(name = "requestPage", value = "页号",required = true,defaultValue = "1"),
            @ApiImplicitParam(name = "movieNumberPerPage", value = "每页电影数",required = true)})
    @GetMapping("/listInfo/{requestPage}/{movieNumberPerPage}")
    public Result listInfo(@PathVariable int requestPage,@PathVariable int movieNumberPerPage){

        return movieService.listInfo(requestPage,movieNumberPerPage);
    }







}
