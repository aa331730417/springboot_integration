package com.luqi.demo.service;

import com.luqi.demo.mapper.RoleMapper;
import com.luqi.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/18 16:41
 * @Version 1.0
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getRoles(Integer userId) {
        return roleMapper.selectByUserId(userId);
    }
}
