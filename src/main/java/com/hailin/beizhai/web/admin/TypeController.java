package com.hailin.beizhai.web.admin;

import com.hailin.beizhai.po.Type;
import com.hailin.beizhai.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Name: TypeController
@Author: zhouhailin
@Date: 2021/2/13
@Time: 8:16 下午
@Description： 
*/
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) //一页是10个 id降序
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type){
        System.out.println("post");
        Type t=typeService.saveType(type);
        if (t==null){
            //
        }else{
            //
        }

        return "redirect:/admin/types";
    }
}
