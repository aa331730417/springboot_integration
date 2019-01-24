package com.luqi.demo.web;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luqi.demo.model.User;
import com.luqi.demo.service.UserService;
import com.luqi.demo.utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/21 10:26
 * @Version 1.0
 */
@Controller
public class ShiroController {

    @Autowired
    private UserService userService;

    /**
     * 退出的时候是get请求，主要是用于退出
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, boolean rememberMe) {
        String msg = null;
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword(), rememberMe);
        try {
            // 调用后就会进去我们自定义的realm中的doGetAuthenticationInfo()方法。
            subject.login(token);
            return "redirect:index";
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            request.setAttribute("msg", msg);
            return "login";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            request.setAttribute("msg", msg);
            return "login";
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            request.setAttribute("msg", msg);
            return "login";
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            request.setAttribute("msg", msg);
            return "login";
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            request.setAttribute("msg", msg);
            return "login";
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            request.setAttribute("msg", msg);
            return "login";
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            request.setAttribute("msg", msg);
            return "login";
        }
    }

    /**
     * 首页
     */
    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "user")
    public String user(HttpServletRequest request,
                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List list = userService.getUsers();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        request.setAttribute("pageInfo", pageInfo);
        return "user/list";
    }

    /**
     * 登出
     */
    @RequestMapping(value = "logout")
    public String logout() {
        return "index";
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "add")
    public String add() {
        return "user/add";
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "update")
    public String update() {
        return "user/update";
    }

    @RequiresPermissions("delete")
    @RequestMapping(value = "delete")
    public String delete() {
        return "user/delete";
    }

}
