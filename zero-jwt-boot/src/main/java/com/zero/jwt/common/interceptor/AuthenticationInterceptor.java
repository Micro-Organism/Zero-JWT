package com.zero.jwt.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zero.jwt.common.annotation.PassToken;
import com.zero.jwt.common.annotation.SystemUserLoginToken;
import com.zero.jwt.domain.entity.SystemUserEntity;
import com.zero.jwt.service.UserService;
import com.zero.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;


public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        //  get token  from http header
        String token = httpServletRequest.getHeader("token");
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //check is included @passtoken annotation? jump if have
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //check is included @SystemUserLoginToken?
        if (method.isAnnotationPresent(SystemUserLoginToken.class)) {
            SystemUserLoginToken SystemUserLoginToken = method.getAnnotation(SystemUserLoginToken.class);
            if (SystemUserLoginToken.required()) {
                // excute verify
                if (token == null) {
                    throw new RuntimeException("token invalid，please login again");
                }
                // get  user id  from  token
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                SystemUserEntity user = userService.findUserById(userId);
                if (user == null) {
                    throw new RuntimeException("user is not exist，please login again");
                }
                // verify token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                    if (JWT.decode(token).getExpiresAt().before(new Date())) {
                        throw new RuntimeException("token Expires");
                    }

                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }

                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
