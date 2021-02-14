package com.hailin.beizhai.web.admin;

import com.hailin.beizhai.po.Type;
import com.hailin.beizhai.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;

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
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        System.out.println("editinput ");
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //编辑
    @PostMapping("/types/{id}")
    public String editPost(@PathVariable Long id, RedirectAttributes attributes, @Valid Type type, BindingResult result) {//type binding 相邻
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {//重复校验
            result.rejectValue("name", "nameError", "该分类已经存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    //新增
    @PostMapping("/types")
    public String post(RedirectAttributes attributes, @Valid Type type, BindingResult result) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {//重复校验
            result.rejectValue("name", "nameError", "该分类已经存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    //删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
