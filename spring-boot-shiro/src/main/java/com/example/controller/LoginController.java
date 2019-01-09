package com.example.controller;

import com.example.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("index")
    @RequiresRoles("admin")
    public String index(){
        return "index";
    }

    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("username")String username,
                            @RequestParam("password")String password,
                            HttpSession session){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            User user = (User)subject.getPrincipal();
            session.setAttribute("user",user);
            return "index";
        }catch (Exception e){
            return "login";
        }
    }
    @RequestMapping("/hello")
    @RequiresRoles(value ={"admin"},logical = Logical.OR)
    @RequiresPermissions(value=)
    public String hello(){
        return "hello";
    }
}
