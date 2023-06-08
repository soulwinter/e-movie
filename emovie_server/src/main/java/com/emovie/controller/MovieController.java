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


@Slf4j
@Api(value = "电影controller",tags = {"电影接口"})
@RestController
@RequestMapping("/movie")
public class MovieController {



    @Resource
    IMovieService movieService;


    @ApiOperation("获得分页电影信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "requestPage", value = "页号",required = true,defaultValue = "1"),
            @ApiImplicitParam(name = "movieNumberPerPage", value = "每页电影数",required = true)})
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
    @PostMapping("/searchRecommendation")
    public Result searchRecommendation(@RequestBody String movieInfoString){
        System.out.println(movieInfoString);
        return movieService.searchRecommendation(movieInfoString);
    }

    /**
     * 筛选的所有选项
     * @return
     */
    @GetMapping("/filterItem")
    public Result getFilterItem(){
        return movieService.getFilterItem();
    }


    /**
     * 点击搜索时推荐的电影返回电影简介
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getMovieById(@PathVariable double id){
        return movieService.getMovieById((int)id);
    }

    /**
     * 更新电影Basic信息的接口
     * @param basic
     * @return
     */
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody Movie basic){
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
     */
    @GetMapping("/deleteKeyWord")
    public Result deleteKeyWord(@RequestParam String KeyWord,@RequestParam int id){ return movieService.deleteKeyWord(KeyWord,id);}

    /**
     * 电影Genre的新增接口
     */
    @GetMapping("/addGenre")
    public Result addGenre(@RequestParam String Genre,@RequestParam int id){ return movieService.addGenre(Genre,id);}


    /**
     * 电影Genre的删除接口
     */
    @GetMapping("/deleteGenre")
    public Result deleteGenre(@RequestParam String Genre,@RequestParam int id){ return movieService.deleteGenre(Genre,id);}
}
