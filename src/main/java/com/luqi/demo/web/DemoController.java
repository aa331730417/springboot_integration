package com.luqi.demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/17 9:26
 * @Version 1.0
 */
@RestController
public class DemoController {

    @Value("${server.port}")
    private String prot;

    @RequestMapping("hello")
    public String hello(){
        return "hello world！I'm" + prot;
    }

}
