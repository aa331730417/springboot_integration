package com.luqi.demo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luqi.demo.model.User;
import com.luqi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/17 9:52
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页插件demo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("getUsers")
    public PageInfo<User> getUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List list = userService.getUsers();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

}
