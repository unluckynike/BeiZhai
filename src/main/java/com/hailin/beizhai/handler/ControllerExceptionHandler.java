package com.hailin.beizhai.handler;
/*
@Name: ControllerExceptionHandler
@Author: zhouhailin
@Date: 2021/2/12
@Time: 9:36 下午
@Description： 异常处理
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        //记录
        logger.error("Request URL:{},Exception :{}", request.getRequestURI(), e);
        //拦截
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
           //放行定义状态码
            throw e;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURI());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
