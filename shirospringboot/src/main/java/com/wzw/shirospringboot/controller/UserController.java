package com.wzw.shirospringboot.controller;

import com.wzw.shirospringboot.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/add")
    public String add(){
        //返回到templates/user下面的add.html页面
        return "/user/add";
    }
    @RequestMapping("/update")
    public String update(){
        //返回到templates/user下面的update.html页面
        return "/user/update";
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(User user, Model model){
        //1、获取当前主体Subject
        Subject subject= SecurityUtils.getSubject();
        //2、创建令牌
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try {
            //3、登录
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("errorMsg","密码错误");
            return "login";
        }
        return "success";
    }

    @RequestMapping("/noAuthc")
    public String noAuthc(){
        return "noauthc";
    }
}
