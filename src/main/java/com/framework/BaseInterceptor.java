package com.framework;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huhu on 2018/10/8.
 */
public class BaseInterceptor implements HandlerInterceptor {
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//    httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET");
    return true;
  }

  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}
