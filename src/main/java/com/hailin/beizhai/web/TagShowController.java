package com.hailin.beizhai.web;

import com.hailin.beizhai.po.Tag;
import com.hailin.beizhai.service.BlogService;
import com.hailin.beizhai.service.TagService;
import com.hailin.beizhai.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
@Name: TagShowController
@Author: zhouhailin
@Date: 2021/2/18
@Time: 2:23 下午
@Description： 前端 标签展示 避免与admin重名
*/
@Controller
public class TagShowController {

    @Autowired
    private TagService TagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String Tags(@PathVariable Long id, //id 参数绑定
                        @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Tag> tags=TagService.listTagTop(1000);//相当于查询全表
        if (id==-1){
            id=tags.get(0).getId();//进入未选择分类
        }
        BlogQuery blogQuery=new BlogQuery();
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));//分页查询
        model.addAttribute("activeTagId",id);
        return "tags";
    }

}
