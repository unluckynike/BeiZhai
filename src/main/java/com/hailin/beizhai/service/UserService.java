package com.hailin.beizhai.service;

import com.hailin.beizhai.po.User;

public interface UserService {

    User checkUser(String username,String password);
}
