package com.hailin.beizhai.web.admin;
/*
@Name: BlogController
@Author: zhouhailin
@Date: 2021/2/13
@Time: 5:05 下午
@Description： 
*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @GetMapping("/blogs")
    public String blogs(){
        System.out.println("blogs get in");
        return "admin/blogs";
    }

}
