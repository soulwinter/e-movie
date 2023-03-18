package com.emovie.service.impl;

import com.emovie.dao.UserDao;
import com.emovie.entity.User;
import com.emovie.service.IUserService;
import com.emovie.util.MD5;
import com.emovie.util.RegexUtils;
import com.emovie.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDao userDao;

    @Override
    public Result login(String telephone, String password) {


        Result result=null;
        try {
            //判断的字段是否为空，若为空 直接返回
            Assert.notNull(telephone, "手机号不能为空");
            Assert.notNull(password, "密码不能为空");

            //判断手机号是否合法
            Assert.isTrue(!RegexUtils.isPhoneInvalid(telephone),"手机号格式错误，不合法");

            //通过手机号和密码 去数据库查找
            User userEntity = userDao.getUserByTelephoneAndPassword(telephone,password);

            System.out.println(userEntity);
            //若没有用户，直接返回
            Assert.notNull(userEntity, "该手机号还未注册或密码有误");

            //如果找到啦 判断有没有token
            if(userEntity.getToken() == null || userEntity.getToken().trim().equals("")){
                //没有token 造一个给他 并更新到数据库中
                log.info("没有tonken");
                String token = MD5.GetMD5Code(telephone + password + Calendar.getInstance().getTimeInMillis());
                log.info("生成token:"+token);
                userEntity.setToken(token);
                userDao.updateUser(userEntity);
            }

            result = Result.ok();
        } catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {

            return result;
        }

    }
}
