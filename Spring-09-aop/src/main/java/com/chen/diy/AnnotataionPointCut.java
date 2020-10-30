package com.chen.diy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


//标注这个类是一个切面
@Aspect
public class AnnotataionPointCut {

    @Before("execution(* com.chen.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("method exec beforr");
    }
    @After("execution(* com.chen.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("method exec after");
    }


    @Around("execution(* com.chen.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前");
        Object proceed = jp.proceed();
        System.out.println("环绕后");
    }

}
