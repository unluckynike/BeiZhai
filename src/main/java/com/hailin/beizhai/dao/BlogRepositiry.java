package com.hailin.beizhai.dao;

import com.hailin.beizhai.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//JpaRepository传递对象和主键类型
//JpaSpecificationExecutor jpa动态查询
public interface BlogRepositiry extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from  Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog  b where b.title like ?1 or b.content like ?1")//数字1 表示传第一个参数
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where b.id= ?1") //访问次数
    int updateViews(Long id);

    //归档 查询年份
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
