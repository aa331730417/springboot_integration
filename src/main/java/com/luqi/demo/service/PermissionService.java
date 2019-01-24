package com.luqi.demo.service;

import com.luqi.demo.mapper.PermissionMapper;
import com.luqi.demo.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/18 16:44
 * @Version 1.0
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> getPermissions(Integer roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }
}
