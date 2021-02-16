package com.hailin.beizhai.dao;

import com.hailin.beizhai.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JpaRepository传递对象和主键类型
//JpaSpecificationExecutor jpa动态查询
public interface BlogRepositiry extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from  Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog  b where b.title like ?1 or b.content like ?1")//数字1 表示传第一个参数
    Page<Blog> findByQuery(String query,Pageable pageable);
}
