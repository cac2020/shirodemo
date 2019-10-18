package com.wjy.shirodemo.controller;

import com.wjy.shirodemo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("authc")
public class AuthcController {

    /**
    * @Desc:登录鉴权成功后跳转地址
    */
    @GetMapping("index")
    public Object index() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        return "login  success:"+user.toString();
    }

    /**
    * @Desc:  admin路径
    */
    @GetMapping("admin")
    public Object admin() {
        return "Welcome Admin";
    }

    /**
     * @Desc:  removable路径
     */
    @GetMapping("removable")
    public Object removable() {
        return "removable";
    }

    /**
     * @Desc:  renewable路径
     */
    @GetMapping("renewable")
    public Object renewable() {
        return "renewable";
    }
}
