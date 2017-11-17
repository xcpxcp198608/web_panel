package com.wiatec.panel.aop;

import com.wiatec.panel.xutils.TextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthShowUsername {

    @Around("execution(* com.wiatec.panel.service.auth.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        Model model = null;
        for(Object o: args){
            if(o instanceof  HttpServletRequest){
                request = (HttpServletRequest) o;
            }
            if(o instanceof  Model){
                model = (Model) o;
            }
        }
        if(request != null && model != null) {
            String username = (String) request.getSession().getAttribute("username");
            if(TextUtil.isEmpty(username)){
                throw new RuntimeException("signin error, sign in again");
            }
            model.addAttribute("username", username);
        }
        return joinPoint.proceed();
    }
}
