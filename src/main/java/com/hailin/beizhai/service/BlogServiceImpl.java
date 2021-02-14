package com.hailin.beizhai.service;

import com.hailin.beizhai.NotFoundException;
import com.hailin.beizhai.dao.BlogRepositiry;
import com.hailin.beizhai.po.Blog;
import com.hailin.beizhai.po.Type;
import com.hailin.beizhai.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/*
@Name: BlogServiceImpl
@Author: zhouhailin
@Date: 2021/2/14
@Time: 2:44 下午
@Description： 
*/

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepositiry blogRepositiry;


    @Override
    public Blog getBlog(Long id) {
        return blogRepositiry.findById(id).get();
    }

    //查询
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        //动态查询条件
        return blogRepositiry.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if ("".equals(blog.getTitle()) && (blog.getTitle() != null)) {
                    //title  like 字段模糊查询
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                // 分类
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //推荐
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepositiry.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepositiry.findById(id).get();
        if (b == null) {
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog, b);
        return blogRepositiry.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepositiry.deleteById(id);
    }
}
