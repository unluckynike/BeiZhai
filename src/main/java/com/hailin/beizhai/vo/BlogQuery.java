package com.hailin.beizhai.vo;

/*
@Name: BlogQuery
@Author: zhouhailin
@Date: 2021/2/14
@Time: 4:12 下午
@Description： 
*/
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommendl=" + recommend +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public BlogQuery() {
    }
}
