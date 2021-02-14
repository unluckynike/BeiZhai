package com.hailin.beizhai.dao;

import com.hailin.beizhai.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//JpaRepository传递对象和主键类型
//JpaSpecificationExecutor jpa动态查询
public interface BlogRepositiry extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
}
