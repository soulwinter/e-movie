package com.emovie.service;

import com.emovie.entity.User;
import com.emovie.util.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface IUserService {

    /**
     * 使用密码登录
     * @param telephone
     * @param password
     * @return
     */
    Result loginWithPassword(String telephone, String password);

    /**
     * 发送验证码
     * @param telephone
     * @param mode
     * @return
     */
    Result sendCode(String telephone,String mode);

    /**
     * 使用验证码登录
     * @param phone
     * @param code
     * @param session
     * @return
     */
    Result loginWithCode(String phone, String code, HttpSession session);

    /**
     * 使用注册功能
     * @param telephone
     * @param code
     * @return
     */
    Result register(String telephone, String password,String username,String code);

}
