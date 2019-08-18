package com.mall.service.impl;

import com.mall.common.ServerResponseBody;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponseBody<User> login(String username, String password) {
        int resultCount=userMapper.checkUsername(username);
        if (0==resultCount){
            return ServerResponseBody.createByError("用户名不存在");
        }
        //todo 密码登陆
        //检查用户名密码是否一致
        User user=userMapper.selectLogin(username, password);
        if (null==user){
            return ServerResponseBody.createByError("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponseBody.createBySuccess("登陆成功", user);
    }
}
