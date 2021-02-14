package com.hailin.beizhai.web.admin;
/*
@Name: BlogController
@Author: zhouhailin
@Date: 2021/2/13
@Time: 5:05 下午
@Description： 
*/

import com.hailin.beizhai.po.Blog;
import com.hailin.beizhai.service.BlogService;
import com.hailin.beizhai.service.TypeService;
import com.hailin.beizhai.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog,
                        Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }

    //局部渲染
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog,
                         Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }



}
