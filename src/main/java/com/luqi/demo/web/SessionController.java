package com.luqi.demo.web;

import com.luqi.demo.dto.JsonResult;
import com.luqi.demo.model.User;
import com.luqi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/18 14:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/session")
public class SessionController {


    @Value("${server.port}")
    private String prot;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "login/{id}", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> login(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        JsonResult r = new JsonResult();
        HttpSession session = request.getSession();
        User user = null;
        if (session.getAttribute("user") == null) {
            user = userService.get(id);
            session.setAttribute("user", user);
        } else {
            user = (User) session.getAttribute("user");
        }
        r.setResult(user);
        r.setStatus("I'm port:"+ prot + "sessionId:" + session.getId());
        return ResponseEntity.ok(r);
    }

}
