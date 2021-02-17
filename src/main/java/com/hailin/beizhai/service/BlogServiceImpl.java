package com.hailin.beizhai.service;

import com.hailin.beizhai.NotFoundException;
import com.hailin.beizhai.dao.BlogRepositiry;
import com.hailin.beizhai.po.Blog;
import com.hailin.beizhai.po.Type;
import com.hailin.beizhai.util.MarkdownUtil;
import com.hailin.beizhai.util.MyBeanUtils;
import com.hailin.beizhai.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Blog getAndConvert(long id) {
        Blog blog=blogRepositiry.findById(id).get();
        if (blog==null){
            throw new NotFoundException("博客文章不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);//保存一份原始值
        String content=b.getContent();
        b.setContent(MarkdownUtil.markdownToHtmlExtensions(content));//博客文章转换成markdown
        blogRepositiry.updateViews(id);//访问次数
        return b;
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

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepositiry.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {//搜索
        return blogRepositiry.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        return blogRepositiry.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            //新增
            blog.setCreateTime(new Date());//初始化文章信息
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            //修改
            blog.setUpdateTime(new Date());
        }
        return blogRepositiry.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepositiry.findById(id).get();
        if (b == null) {
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepositiry.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepositiry.deleteById(id);
    }
}
