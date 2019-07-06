package com.framework;

/**
 * Created by Administrator on 2018/7/17.
 */

import com.model.Userinfo;
import com.utils.MemoryData;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * Handler执行完成之后调用这个方法
     */
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exc
    ) throws Exception {

    }

    /**
     * Handler执行之后，ModelAndView返回之前调用这个方法
     */
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {

    }
    /**
     * Handler执行之前调用这个方法
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();    //url请求
        String contextPath = request.getContextPath();  //项目的根地址
        String url = requestUri.substring(contextPath.length());

//        HttpSession session = request.getSession();
//        if (url.indexOf("/login") != -1){
//            return true;
//        } else {
//            String loginUrl = contextPath + "/login";
//            Userinfo userinfo = (Userinfo) session.getAttribute("userInfo");
//            if (userinfo != null){
//                HttpSession oldSession = MemoryData.getSessionMap(userinfo.getUserCode());
//                if (session.getId().equals(oldSession.getId())){
//                    return true;
//                } else {
//                    response.sendRedirect(loginUrl );
//                    return false;
//                }
//            } else {
//                response.sendRedirect(loginUrl );
//                return false;
//            }
//        }


       if(MemoryData.getSessionMap("123")!=null)
       {
         return true;
       }else {
         response.sendRedirect(contextPath);
         return false;
       }
//      Employee userinfo = (Employee) request.getSession().getAttribute("userinfo");
//      if (userinfo != null) {
//        String sessionid1 = MemoryData.getSessionIDMap().get(userinfo.getEmpCode() + "");
//        HttpSession session = request.getSession();
//        Long nowTime = System.currentTimeMillis();
//        boolean returnCheck = true;
//        if (sessionid1.equals(request.getSession().getId())) {
//          Map lastRequest = (Map) session.getAttribute("lastRequest");
//          if (lastRequest == null){
//            lastRequest = new HashMap();
//            session.setAttribute("lastRequest", lastRequest);
//          } else {
//            Long lastTime = (Long) lastRequest.get(requestUri);
//            if (lastTime != null){
//              if (nowTime - lastTime < 500){
//                returnCheck = false;
//              }
//            }
//          }
//          lastRequest.put(requestUri, nowTime);
//          return returnCheck;
//        } else { //如果请求的sessionID和此账号Map中存放的sessionID不一致，跳转到登陆页
//          response.sendRedirect(contextPath);
//          return false;
//        }
//      }

  }

}
