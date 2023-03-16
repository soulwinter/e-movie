package com.emovie.controller;

import com.emovie.entity.User;
import com.emovie.service.IUserService;
import com.emovie.util.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import java.util.Calendar;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService iUserService;

    @PostMapping("/login")
    public Result login(@RequestParam(value = "telephone") String telephone,
                        @RequestParam(value = "password") String password){

        log.info("Action login~");
        Result result=iUserService.login(telephone, password);
        log.info("Done login Action!");
        return result;
    }
}
