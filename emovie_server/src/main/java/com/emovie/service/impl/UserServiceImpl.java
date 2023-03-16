package com.emovie.service.impl;

import com.emovie.dao.UserDao;
import com.emovie.entity.User;
import com.emovie.service.IUserService;
import com.emovie.util.MD5;
import com.emovie.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;

@Service
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

            //通过手机号和密码 去数据库查找
            User userEntity = userDao.getUserByTelephoneAndPassword(telephone,password);

            //若没有用户，直接返回
            Assert.notNull(userEntity, "该手机号还未注册或密码有误");

            //如果找到啦 判断有没有token
            if(userEntity.getToken() == null || userEntity.getToken().trim().equals("")){
                //没有token 造一个给他 并更新到数据库中
                String token = MD5.GetMD5Code(telephone + password + Calendar.getInstance().getTimeInMillis());
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
