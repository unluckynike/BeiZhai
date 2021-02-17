package com.hailin.beizhai.service;
import com.hailin.beizhai.dao.CommentRepositiry;
import com.hailin.beizhai.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
@Name: CommentServiceImpl
@Author: zhouhailin
@Date: 2021/2/17
@Time: 3:18 下午
@Description： 
*/
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepositiry commentRepositiry;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=Sort.by("createTime");
        List<Comment> comments=commentRepositiry.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }


    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();//公共容器
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();// -1 默认值
        if (parentCommentId!=-1){
            //子父级关系
            comment.setParentComment(commentRepositiry.findById(parentCommentId).get());//findOne()
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepositiry.save(comment);
    }
}
