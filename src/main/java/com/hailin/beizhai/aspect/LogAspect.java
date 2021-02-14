package com.hailin.beizhai.aspect;
/*
@Name: LogAspect
@Author: zhouhailin
@Date: 2021/2/12
@Time: 10:22 下午
@Description： 
*/

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //声明切面 所有请求
    @Pointcut("execution(* com.hailin.beizhai.web.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURI().toString();//url
        String ip=request.getRemoteAddr();//获取ip
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();//获取类名
        Object[] args=joinPoint.getArgs();//获取参数
        Requestlog requestlog=new Requestlog(url,ip,classMethod,args);
        logger.info("---------doBefore--------");
        logger.info("Requestlog:{}",requestlog);

    }

    @After("log()")
    public void doAfter() {
        logger.info("---------doAfter--------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result:{}" ,result);
    }

    //内部类
    private class Requestlog {
        private String url; //请求
        private String ip;  //访问ip
        private String classMethod; //调用方法
        private Object[] args; //参数

        public Requestlog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "Requestlog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
