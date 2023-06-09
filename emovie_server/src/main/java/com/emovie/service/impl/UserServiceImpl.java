package com.emovie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.emovie.dao.UserDao;
import com.emovie.dto.UserDTO;
import com.emovie.entity.User;
import com.emovie.service.IUserService;
import com.emovie.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.emovie.util.RedisConstants.*;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDao userDao;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result loginWithPassword(String telephone, String password) {


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

            //给他一个token
            Map<String,String> payload=new HashMap<>();
            payload.put("id",String.valueOf(userEntity.getId()));
            payload.put("telephone",userEntity.getTelephone());
            payload.put("username",userEntity.getUsername());
            payload.put("type", String.valueOf(userEntity.getType()));
            String token = JWTUtils.getToken(payload);


            HashMap<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("username",userEntity.getUsername());
            map.put("type",userEntity.getType());
            result = Result.ok(map);
        } catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {

            return result;
        }
    }



    @Override
    public Result sendCode(String phone,String mode) {

        System.out.println("phone:"+phone);
        //1. 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            //2.如果不符合，返回错误信息
            log.error("手机号格式错误");
            return Result.fail("手机号格式错误");
        }

        //3. 符合，生成验证码
        String code = RandomUtil.randomNumbers(6);

        //4. 保存验证码到redis
        //登录模式
        if(mode.equals(Constants.LOGIN_MODE)){
            stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone,code,CODE_TTL, TimeUnit.MINUTES);
        }
        else if(mode.equals(Constants.REGISTER_MODE)){
            stringRedisTemplate.opsForValue().set(REGISTER_CODE_KEY+phone,code,CODE_TTL, TimeUnit.MINUTES);
        }


        //5. 发送验证码
        log.debug("发送短信验证码成功，验证码:{}",code);

        //返回ok
        return Result.ok();

    }


    @Override
    public Result loginWithCode(String telephone, String code, HttpSession session) {
        //2. 校验验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + telephone);

        if (cacheCode == null || !cacheCode.toString().equals(code)){
            //3.不一致，报错
            return Result.fail("验证码错误或过期");
        }

        //4.一致，根据手机号查询用户
        User userEntity = userDao.getUserByTelephone(telephone);

        //5. 判断用户是否存在
        if (userEntity == null){
            //6.不存在 返回手机号未注册
            return Result.fail("手机号未注册！");
        }

        //7.保存用户信息到token
        //7.1 生成token，作为登录令牌
//        if(userEntity.getToken() == null || userEntity.getToken().trim().equals("")){
//            //没有token 造一个给他 并更新到数据库中
//            log.info("没有tonken");
//            String token = MD5.GetMD5Code(telephone + userEntity.getPassword() + Calendar.getInstance().getTimeInMillis());
//            log.info("生成token:"+token);
//            userEntity.setToken(token);
//            userDao.updateUser(userEntity);
//        }
        //给他一个token
        Map<String,String> payload=new HashMap<>();
        payload.put("id",String.valueOf(userEntity.getId()));
        payload.put("telephone",userEntity.getTelephone());
        payload.put("username",userEntity.getUsername());
        payload.put("type", String.valueOf(userEntity.getType()));
        String token = JWTUtils.getToken(payload);

//        //7.2 将User对象转为String存储
//        UserDTO userDTO = BeanUtil.copyProperties(userEntity, UserDTO.class);
//

//        //7.3 存储
//        String token=userEntity.getToken();
//        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+token,JSONUtil.toJsonStr(userDTO));
//
//        //7.4 设置token的有效期
//        stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.MINUTES);

        //8.返回User信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("username",userEntity.getUsername());
        map.put("type",userEntity.getType());
        return Result.ok(map);
    }

    @Override
    public Result register(String telephone, String password,String username,String code){
        Result result=null;
        try {
            //2. 校验验证码
            String cacheCode = stringRedisTemplate.opsForValue().get(REGISTER_CODE_KEY + telephone);

            if (cacheCode == null || !cacheCode.toString().equals(code)){
                //3.不一致，报错
                return Result.fail("验证码错误或过期");
            }


            //判断的字段是否为空，若为空 直接返回
            if(telephone.equals("")){  result = Result.fail("请填写手机号！");return result;}
            if(password.equals("")){  result = Result.fail("请填写密码！");return result;}
            if(username.equals("")){  result = Result.fail("请填写用户名！");return result;}
//            Assert.notNull(telephone, "手机号不能为空");
//            Assert.notNull(password, "密码不能为空");

            //先通过手机号访问数据库，如果已经注册 则返回错误
            User userEntity = userDao.getUserByTelephone(telephone);

            if(userEntity!=null){
                result = Result.fail("该账号已注册！");
                return result;
            }
            User userentity = new User();
            userentity.setUsername(username);
            userentity.setPassword(password);
            userentity.setTelephone(telephone);
            userDao.register(userentity);

            result = Result.ok();
        } catch (Exception e) {
            result = Result.fail(e.getMessage());
        } finally {
            return result;
        }

    }


}
