package com.hailin.beizhai.web;
/*
@Name: IndexController
@Author: zhouhailin
@Date: 2021/2/12
@Time: 9:30 下午
@Description： 
*/

import com.hailin.beizhai.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        System.out.println("mapping get in");
//       int i = 9 / 0;
//        System.out.println("sss");
//        String blog=null;
//        if(blog==null){
//            throw   new NotFoundException("页面不存在");
//        }
        System.out.println("=====index=======");
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }
}
