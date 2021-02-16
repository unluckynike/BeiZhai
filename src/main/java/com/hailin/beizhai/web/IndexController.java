package com.hailin.beizhai.web;
/*
@Name: IndexController
@Author: zhouhailin
@Date: 2021/2/12
@Time: 9:30 下午
@Description： 
*/

import com.hailin.beizhai.service.BlogService;
import com.hailin.beizhai.service.TagService;
import com.hailin.beizhai.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        //首页数据
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));//右侧分类
        model.addAttribute("tags", tagService.listTagTop(6));//右侧标签
        model.addAttribute("recommendBlogs", blogService.listBlogTop(5));//右侧推荐
        return "index";
    }

    //搜索
    @PostMapping("/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,
                         @RequestParam String query) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }
}
