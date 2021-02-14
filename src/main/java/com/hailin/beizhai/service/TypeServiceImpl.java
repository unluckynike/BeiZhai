package com.hailin.beizhai.service;

import com.hailin.beizhai.NotFoundException;
import com.hailin.beizhai.dao.TypeRepository;
import com.hailin.beizhai.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
@Name: TypeServiceImpl
@Author: zhouhailin
@Date: 2021/2/13
@Time: 8:05 下午
@Description： 
*/
@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public Type getTypeByName(String name) {
        System.out.println("111"+typeRepository.findByName(name));
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {//分页
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeRepository.findById(id).get();
        if (t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);//type 赋值到t
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
          typeRepository.deleteById(id);
    }
}
