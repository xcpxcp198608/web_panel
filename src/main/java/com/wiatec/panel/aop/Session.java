package com.wiatec.panel.aop;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.xutils.TextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class Session {

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
            String ref = request.getHeader("Referer");
            if(ref == null || !ref.contains("/panel")){
                throw new RuntimeException("signin error");
            }
            String username = getUserName(request);
            if(TextUtil.isEmpty(username)){
                throw new RuntimeException("signin error, sign in again");
            }
            model.addAttribute("username", username);
        }
        return joinPoint.proceed();
    }

    public static String getUserName(HttpServletRequest request){
        String sessionId = request.getCookies()[0].getValue();
        HttpSession session = SessionListener.idSessionMap.get(sessionId);
        if(session == null){
            throw new RuntimeException("sign in expires");
        }
        return (String) session.getAttribute("username");
    }
}
