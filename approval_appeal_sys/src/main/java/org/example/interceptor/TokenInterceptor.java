package org.example.interceptor;


import org.example.entity.User;
import org.example.exception.CustomException;
import org.example.response.ResultEnum;
import org.example.util.JWTUtil;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        //判断请求方式是不是预检请求，如果是预检请求直接返回true,放行
        if(method.equals(HttpMethod.OPTIONS.name())){
            return true;
        }
        String token = request.getHeader("Auth");
        User user = null;
        try {
            user = JWTUtil.parseToken(token);//用工具类校验令牌
            HttpSession session = request.getSession();
            session.setAttribute("user",user);//将解析出来的user对象放入session以便在控制器中知道当前用户是谁
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResultEnum.INVALID_TOKEN);//抛出token令牌异常
        }
    }
}
