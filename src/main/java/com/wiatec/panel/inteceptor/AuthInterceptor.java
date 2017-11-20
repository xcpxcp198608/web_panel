package com.wiatec.panel.inteceptor;

import com.wiatec.panel.xutils.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String ref = request.getHeader("Referer");
//        if(ref == null || !ref.contains("/panel")){
//            throw new RuntimeException("signin error");
//        }
//        String username = (String) request.getSession().getAttribute("username");
//        Logger.getLogger("").debug("before interceptor " + username);
//        if(username == null){
//            throw new RuntimeException("signin error");
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        Logger.getLogger("").debug("after interceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
