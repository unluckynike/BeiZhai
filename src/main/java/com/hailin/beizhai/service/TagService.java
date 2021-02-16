package com.hailin.beizhai.service;

import com.hailin.beizhai.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);//根据传值大小查询

    List<Tag> listTag(String ids);//1,2,3

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
