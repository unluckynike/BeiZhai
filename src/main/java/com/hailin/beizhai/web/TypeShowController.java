package com.hailin.beizhai.web;

import com.hailin.beizhai.po.Type;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
@Name: TypeShowController
@Author: zhouhailin
@Date: 2021/2/18
@Time: 2:23 下午
@Description： 前端 分类展示 避免与admin重名
*/
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, //id 参数绑定
                        @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Type> types=typeService.listTypeTop(1000);//相当于查询全表
        if (id==-1){
            id=types.get(0).getId();//进入未选择分类
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));//分页查询
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
