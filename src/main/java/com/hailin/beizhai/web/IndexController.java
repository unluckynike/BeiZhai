package com.hailin.beizhai.web;
/*
@Name: IndexController
@Author: zhouhailin
@Date: 2021/2/12
@Time: 9:30 下午
@Description： 
*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }
}
