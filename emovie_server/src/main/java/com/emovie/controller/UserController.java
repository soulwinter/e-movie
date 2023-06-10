package com.emovie.controller;

import com.emovie.entity.User;
import com.emovie.service.IUserService;
import com.emovie.util.Result;
//import com.sun.org.apache.bcel.internal.classfile.Code;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
//import sun.security.provider.MD5;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;



@Slf4j
@Api(value = "用户Controller",tags = {"用户访问接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService iUserService;

    /**
     * 登录功能
     * @param telephone
     * @param password
     * @return
     */


    //说明是什么方法(可以理解为方法注释)
    @ApiOperation("使用密码登录")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParams({@ApiImplicitParam(name = "telephone", value = "手机号",required=false),
                        @ApiImplicitParam(name = "password", value = "密码",required=false)})
    @PostMapping("/loginWithPassword")
    public Result loginWithPassword(@RequestParam(value = "telephone",required = false) String telephone,
                        @RequestParam(value = "password",required = false) String password){

        log.info("Action login~");
        Result result=iUserService.loginWithPassword(telephone, password);
        log.info("Done login Action!");
        return result;
    }

    /**
     * 发送手机验证码
     * @param telephone
     * @param mode
     * @return
     */


    @ApiOperation(value = "发送验证码", notes = "根据传入的手机号和发送类型发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true),
            @ApiImplicitParam(name = "mode", value = "是登录模式还是注册模式", required = true)
    })
    @PostMapping("code")
    public Result sendCode(@RequestParam("telephone") String telephone,
                           @RequestParam("mode") String mode) {
        return iUserService.sendCode(telephone,mode);
    }

    /**
     * 使用验证码登录
     * @param telephone
     * @param code
     * @param session
     * @return
     */


    @ApiOperation(value = "验证码登录", notes = "通过手机号和验证码进行登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = false),
            @ApiImplicitParam(name = "code", value = "验证码", required = false)
    })
    @PostMapping("/loginWithCode")
    public Result loginWithCode(@RequestParam(value = "telephone") String telephone,
                                @RequestParam(value = "code") String code,
                                HttpSession session){

        return iUserService.loginWithCode(telephone, code ,session);
    }

    /**
     * 注册
     * @param telephone
     * @param password
     * @param username
     * @param code
     * @return
     */


    @ApiOperation(value = "用户注册", notes = "通过手机号、密码、用户名和验证码进行注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true)
    })
    @PostMapping("/register")
    public Result register(@RequestParam(value = "telephone") String telephone,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "username") String username,
                            @RequestParam(value = "code") String code){
        Result result=iUserService.register(telephone,password,username,code);
        return result;
    }



}
