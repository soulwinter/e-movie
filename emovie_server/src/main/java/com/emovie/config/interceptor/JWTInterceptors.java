package com.emovie.config.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emovie.dto.UserDTO;
import com.emovie.util.JWTUtils;
import com.emovie.util.RedisConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.emovie.util.RedisConstants.LOGIN_USER_KEY;
import static com.emovie.util.RedisConstants.LOGIN_USER_TTL;


@Slf4j
public class JWTInterceptors implements HandlerInterceptor {

    private StringRedisTemplate redisTemplate;

    public JWTInterceptors(StringRedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //拦截器取到请求先进行判断，如果是OPTIONS请求，则放行
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("Method:OPTIONS");
            return true;
        }

        log.info("被拦截了");

        Map<String,Object> map = new HashMap<>();
        // 获取请求头中令牌
        String token = request.getHeader("Authorization");
        System.out.println("token: " + token);
        try {
            // 验证令牌
                //先看redis中有没有，如果有，就使用,并延长过期时间
            String key= LOGIN_USER_KEY+token;
            String json = redisTemplate.opsForValue().get(key);
            if(StrUtil.isNotBlank(json)){
                UserDTO userDTO = JSONUtil.toBean(json, UserDTO.class);
                redisTemplate.expire(key,LOGIN_USER_TTL, TimeUnit.DAYS);//一天过期
            }else {
                //如果redis中没有，就走jwt验证，并存到redis中设置过期时间1天
                DecodedJWT verify = JWTUtils.verify(token);
                log.info("用户【"+verify.getClaim("telephone").asString()+"】正在访问");
                String telephone = verify.getClaim("telephone").asString();
                String username = verify.getClaim("username").asString();
                String id= verify.getClaim("id").asString();
                String type= verify.getClaim("type").asString();
                UserDTO userDTO = new UserDTO(Long.valueOf(id), username, telephone, Integer.valueOf(type));
log.debug(userDTO.toString());
                redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(userDTO),LOGIN_USER_TTL,TimeUnit.DAYS);

            }


            return true;  // 放行请求
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","无效签名！");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg","算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","token无效！");
        }
        map.put("state",false);  // 设置状态
        // 将map以json的形式响应到前台  map --> json  (jackson)
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
