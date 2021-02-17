package com.hailin.beizhai.web;

import com.hailin.beizhai.po.Comment;
import com.hailin.beizhai.po.User;
import com.hailin.beizhai.service.BlogService;
import com.hailin.beizhai.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/*
@Name: CommentController
@Author: zhouhailin
@Date: 2021/2/17
@Time: 3:12 下午
@Description： 评论
*/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    //取配置文件值  评论头像
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user= (User)session.getAttribute("user");
        if (user!=null){
            comment.setAvatar(user.getAvatar());//管理员头像
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);//普通访客头像
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}
