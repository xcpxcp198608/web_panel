package com.wiatec.panel.aop;

import com.wiatec.panel.xutils.LoggerUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 注解实现spring aop
 */
@Component
@Aspect
public class Log {

    /**
     * 前置通知方法
     */
    @Before("execution(* com.wiatec.panel.service.*.*(..))")
    public void before() {
        LoggerUtil.d("before - execute");
    }

    /**
     * 后置通知方法
     */
    @After("execution(* com.wiatec.panel.service.*.*(..))")
    public void after() {
        LoggerUtil.d("after - execute");
    }

    /**
     * 环绕通知
     * @param joinPoint     代表目标方法对象
     * @throws Throwable    异常
     */
    @Around("execution(* com.wiatec.panel.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行在前置通知前
        LoggerUtil.d("around before");
        /**
         * joinPoint 作为方法参数传入，代表目标方法对象，使用到此参数时必须要执行proceed，并返回结果
         * 否则实际目标方法将不会执行，在所有的通知方法中都可以使用
         */
        Object result = joinPoint.proceed();
        //执行在后置通知前
        LoggerUtil.d("around after");
        return result;
    }

    /**
     * 异常通知
     * @param exception  发生的异常
     */
    @AfterThrowing(value = "execution(* com.wiatec.panel.service.*.*(..))" , throwing = "exception")
    public void afterThrowing(Exception exception) {
        LoggerUtil.d("throwing");
    }

    /**
     * 返回通知，方法正常执行完没有发生异常后执行的通知
     */
    @AfterReturning("execution(* com.wiatec.panel.service.*.*(..))")
    public void afterReturning(){
        LoggerUtil.d("afterReturning");
    }
}
