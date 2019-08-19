package com.mall.controller;

import com.mall.common.Const;
import com.mall.common.ServerResponseBody;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param session  会话对象，将用户信息保存在会话对象中
     * @return 返回服务响应对象
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponseBody<User> login(String username, String password, HttpSession session) {
        ServerResponseBody<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 登出
     *
     * @param session 将会话里的信息删除
     * @return 返回服务响应对象
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponseBody<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponseBody.createBySuccess();
    }

    /**
     * 注册
     *
     * @param user 前端传递过来的用户信息
     * @return 返回服务响应对象
     */
    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponseBody<String> register(User user) {
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponseBody checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

}
