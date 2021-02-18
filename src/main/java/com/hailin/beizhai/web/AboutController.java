package com.hailin.beizhai.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
@Name: AboutController
@Author: zhouhailin
@Date: 2021/2/18
@Time: 8:25 下午
@Description： 关于
*/
@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(){

        return "about";
    }
}
