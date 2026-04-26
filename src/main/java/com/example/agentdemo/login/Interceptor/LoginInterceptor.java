package com.example.agentdemo.login.Interceptor;

import com.example.agentdemo.Utils.UserContext;
import com.example.agentdemo.common.Entity.User;
import com.example.agentdemo.login.Service.jwtTokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private jwtTokenService jwtTokenService;

    @Resource
    private JwtDecoder jwtDecoder;

    /**
     * 在 Controller 方法执行之前调用
     * 返回 true：放行
     * 返回 false：拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 1. 处理预检请求 OPTIONS，避免跨域时被拦截
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 获取 Session
        String accesstoken = request.getHeader("Authorization");

        if(!StringUtils.hasText(accesstoken)){
            writeUnauthorized(response,"用户未登录");
            return false;
        }
        boolean isVaild = false;
        try {
            long userId = jwtTokenService.getUserID(accesstoken);
            UserContext.setUserId(userId);
            isVaild =  jwtTokenService.isVaild(accesstoken);
        }catch (Exception e){
            writeUnauthorized(response,e.getMessage());
        }
        // 3. 判断用户是否登录
        if(isVaild){
            return true;
        }

        // 4. 未登录，返回 401
        writeUnauthorized(response,"用户登录时间过期，请重新登录");
        UserContext.remove();
        return false;
    }

    private static void writeUnauthorized(HttpServletResponse response,String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"" + message + "\"}");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        UserContext.remove();
    }
}
