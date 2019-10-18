package com.wjy.shirodemo.controller;

import com.wjy.shirodemo.PasswordHelper;
import com.wjy.shirodemo.entity.User;
import com.wjy.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    /**
    * @Desc:  登录界面
    */
    @GetMapping("login")
    public Object login() {
        return "Here is Login page";
    }

    /**
     * @Desc:  鉴权不通过转向的界面
     */
    @GetMapping("unauthc")
    public Object unauthc(HttpServletRequest request) {

        return "Here is Unauthc page:"+request.getParameter("msg");
    }

    /**
    * @Desc: 执行登录
    */
    @GetMapping("doLogin")
    public void doLogin(@RequestParam String username, @RequestParam String password,
                        HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        request.getSession(false);
        try {

            try {
                subject.login(token);
                User user = userService.findByUsername(username);
                subject.getSession().setAttribute("user", user);

                response.sendRedirect(request.getContextPath() + "/authc/index");
            } catch (IncorrectCredentialsException ice) {
                response.sendRedirect(request.getContextPath() + "/unauthc?msg=password error!");
            } catch (UnknownAccountException uae) {
                response.sendRedirect(request.getContextPath() + "/unauthc?msg=username error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @Desc:  注册
     */
    @GetMapping("register")
    public Object register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        passwordHelper.encryptPassword(user);
        userService.saveAndFlush(user);
        return "SUCCESS";
    }
}
