package com.nsntc.sell.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Class Name: $CLASS_NAME$
 * Package: com.nsntc.interview.aspect$
 * Description:
 *
 * @author wkm
 * Create DateTime: 2017/11/30$ 下午5:39$
 * Version: 1.0
 */
//@Aspect
//@Component
//public class HttpAspect {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);
//
//    @Pointcut("execution(public * com.nsntc.interview.controller.GirlController.*(..))")
//    public void cut(){}
//
//    @Before("cut()")
//    public void doBefore(JoinPoint joinPoint) {
//
//        LOGGER.info("AOP before");
//
//        ServletRequestAttributes requestAttributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//
//        /** url */
//        LOGGER.info("requestURL={}", request.getRequestURL());
//
//        /** method */
//        LOGGER.info("method={}", request.getMethod());
//
//        /** ip */
//        LOGGER.info("requestIP={}", request.getRemoteAddr());
//
//        /** 类方法 */
//        String className = joinPoint.getSignature().getDeclaringTypeName();
//        String method = joinPoint.getSignature().getName();
//        LOGGER.info("classMethod={}", className + "." + method);
//
//        /** args */
//        LOGGER.info("args={}", joinPoint.getArgs());
//    }
//
//    @After("cut()")
//    public void doAfter () {
//        LOGGER.info("AOP after");
//    }
//
//    @AfterReturning(pointcut = "cut()", returning = "object")
//    public void doAfterRetruning(Object object){
//        LOGGER.info("response={}", object);
//    }
//}
