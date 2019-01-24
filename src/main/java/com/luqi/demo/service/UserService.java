package com.luqi.demo.service;

import com.luqi.demo.mapper.UserMapper;
import com.luqi.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/17 10:26
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers() {
        return userMapper.findUsers();
    }

    public void save(User user) {
        userMapper.insert(user);
    }

    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public void update(User u) {
        userMapper.updateByPrimaryKey(u);
    }

    public User getByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
}
