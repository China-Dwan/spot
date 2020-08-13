package com.business.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class TestAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.business.aop.Sout)")
    public void testCon() {
    }


    @Pointcut("@annotation(com.business.aop.Sout2)")
    public void testCon2() {
    }


    @Before("testCon()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        System.out.println("before");
        System.out.println(getAnno(joinPoint));
    }

    @After("testCon()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("after");
    }

    @Before("testCon2()")
    public void doBefore2() {
        System.out.println("前切");
    }



    public static Map<String,String> getAnno(JoinPoint joinPoint) throws Exception {
        HashMap<String, String> map = new HashMap<>();

        //获取切点的信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Sout sout = (Sout) method.getAnnotation(Sout.class);
        map.put("s1",sout.s1());
        map.put("s2",sout.s2());

        return map;
    }
}
