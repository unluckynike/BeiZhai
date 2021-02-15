package com.hailin.beizhai.web.admin;

import com.hailin.beizhai.po.Tag;
import com.hailin.beizhai.service.TagService;
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

import javax.validation.Valid;

/*
@Name: TageController
@Author: zhouhailin
@Date: 2021/2/15
@Time: 11:15 上午
@Description： 
*/
@Controller
@RequestMapping("/admin")
public class TagController {

    private static final String TAGS="admin/tags";
    private static final String INPUT="admin/tags-input";
    private static final String REDIRECT_TAG="redirect:/admin/tags";
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return TAGS;
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return INPUT;
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return INPUT;
    }

    @PostMapping("tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","不能添加重复标签");
        }
        if (result.hasErrors()){
            return INPUT;
        }
        Tag t=tagService.saveTag(tag);
        if (t==null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_TAG;
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return INPUT;
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return REDIRECT_TAG;
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_TAG;
    }


}
