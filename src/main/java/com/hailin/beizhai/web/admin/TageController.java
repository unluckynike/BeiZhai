package com.hailin.beizhai.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Name: TageController
@Author: zhouhailin
@Date: 2021/2/15
@Time: 11:15 上午
@Description： 
*/
@Controller
@RequestMapping("/admin")
public class TageController {

    @GetMapping("/tags")
    public String tags(){
        return "admin/tags";
    }
}
