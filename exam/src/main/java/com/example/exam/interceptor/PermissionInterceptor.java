package com.example.exam.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author siney
 * @createTime 2020-10-31
 **/
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        System.out.println(request.getMethod() + " " + request.getSession().getAttribute("user"));
//        if(request.getSession().getAttribute("user") != null)return true;
//        return false;
    }
}
