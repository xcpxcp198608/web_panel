package com.wiatec.panel.inteceptor;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.TextUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ref = request.getHeader("Referer");
        if(ref == null || !ref.contains("/panel")){
            throw new XException(EnumResult.ERROR_AUTHORIZE);
        }
        String username = (String) request.getSession().getAttribute("username");
        if(username == null){
            throw new XException(EnumResult.ERROR_AUTHORIZE);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_AUTHORIZE);
        }
        if(modelAndView != null) modelAndView.getModel().put("username", username);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
