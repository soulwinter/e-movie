package com.emovie.config.interceptor;

import com.emovie.util.UserDTOHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.emovie.util.Constants.USER_TYPE_ADMIN;

@Slf4j
public class AdminInterceptors implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("被拦截了 在验证管理员的身份 ");

        Integer type = UserDTOHolder.getUser().getType();
System.out.println(type);
        if(!Objects.equals(type, USER_TYPE_ADMIN)){
            Map<String,Object> map = new HashMap<>();
            map.put("state",false);  // 设置状态
            map.put("data","您没有权限哦~可联系管理员申请权限（20231010@bjtu.edu.cn）");
            String json = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            response.setStatus(401);
            return false;
        }

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
