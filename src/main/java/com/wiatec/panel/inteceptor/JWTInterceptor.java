package com.wiatec.panel.inteceptor;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.JWTUtil;
import com.wiatec.panel.common.utils.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author patrick
 */
public class JWTInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);
    private static final String VALIDATE_TYPE = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(TextUtil.isEmpty(token)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        if(!token.startsWith(VALIDATE_TYPE)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        if(!JWTUtil.validate(token.substring(6))){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN_EXPIRES);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
