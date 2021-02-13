package com.hailin.beizhai.service;
/*
@Name: UserServiceImpl
@Author: zhouhailin
@Date: 2021/2/13
@Time: 4:34 下午
@Description： 
*/

import com.hailin.beizhai.dao.UserRepository;
import com.hailin.beizhai.po.User;
import com.hailin.beizhai.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
