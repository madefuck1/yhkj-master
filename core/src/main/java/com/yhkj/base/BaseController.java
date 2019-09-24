package com.yhkj.base;

import com.yhkj.model.User;
import com.yhkj.service.UserService;
import com.yhkj.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Auther: chen
 * @Date: 2019/3/9
 * @Description:
 */
public class BaseController {

    @Autowired
    UserService userService;

    /**
     * 用户登录之后，才能调用接口
     *
     * @param request
     * @return
     */
    public User getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = Long.valueOf(RedisUtils.getString(token));
        return userService.getById(userId);
    }

    /**
     * 设置token 到cookie
     * 保存token 到redis
     *
     * @param response
     * @return
     */
    public String setToken(HttpServletResponse response, Long userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        RedisUtils.setString(token, userId.toString(),1296000);
        return token;
    }
}
