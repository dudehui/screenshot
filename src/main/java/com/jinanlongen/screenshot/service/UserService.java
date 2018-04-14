package com.jinanlongen.screenshot.service;

import java.util.List;

import com.jinanlongen.screenshot.model.User;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
