package com.hailin.beizhai;
/*
@Name: NotFoundException
@Author: zhouhailin
@Date: 2021/2/12
@Time: 10:02 下午
@Description：  页面找不见
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String messge) {
        super(messge);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
