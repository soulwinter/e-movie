package com.emovie.controller;

import com.emovie.entity.User;
import com.emovie.service.IUserService;
import com.emovie.util.Result;
import com.sun.org.apache.bcel.internal.classfile.Code;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;



@Slf4j
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
    @PostMapping("/register")
    public Result register(@RequestParam(value = "telephone") String telephone,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "username") String username,
                            @RequestParam(value = "code") String code){
        Result result=iUserService.register(telephone,password,username,code);
        return result;
    }



}
