package com.luqi.demo.custom;

import com.luqi.demo.model.Permission;
import com.luqi.demo.model.Role;
import com.luqi.demo.model.User;
import com.luqi.demo.service.PermissionService;
import com.luqi.demo.service.RoleService;
import com.luqi.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/18 16:39
 * @Version 1.0
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        List<Role> roles = roleService.getRoles(user.getId());
        for (Role role : roles) {
            // 添加角色
            authorizationInfo.addRole(role.getRoleName());
            List<Permission> persissions = permissionService.getPermissions(role.getId());
            for (Permission p : persissions) {
                // 添加权限
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String userName = (String) token.getPrincipal();
        //通过username从数据库中查找 User对象
        User user = userService.getByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException();
        } else if (StringUtils.isEmpty(user.getAddr())) {
            // 帐号锁定
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(), //密码
                ByteSource.Util.bytes(userName),
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        return authenticationInfo;
    }

}
