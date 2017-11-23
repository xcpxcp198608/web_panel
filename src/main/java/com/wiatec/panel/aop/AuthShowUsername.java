package com.wiatec.panel.aop;

import com.wiatec.panel.xutils.TextUtil;
import com.wiatec.panel.xutils.result.EnumResult;
import com.wiatec.panel.xutils.result.XException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthShowUsername {

    @Around("execution(* com.wiatec.panel.service.auth.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Object[] args = joinPoint.getArgs();
        Model model = null;
        for(Object o: args){
            if(o instanceof  Model){
                model = (Model) o;
            }
        }
        if(request != null && model != null) {
            String username = (String) request.getSession().getAttribute("username");
            if(TextUtil.isEmpty(username)){
                throw new XException(EnumResult.ERROR_AUTHORIZATION_DEFINED);
            }
            model.addAttribute("username", username);
        }
        return joinPoint.proceed();
    }
}
