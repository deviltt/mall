package com.mall.service.impl;

import com.mall.common.Const;
import com.mall.common.ServerResponseBody;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import com.mall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponseBody<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (0 == resultCount) {
            return ServerResponseBody.createByError("用户名不存在");
        }
        //todo 密码登陆
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        //检查用户名密码是否一致
        User user = userMapper.selectLogin(username, md5Password);
        if (null == user) {
            return ServerResponseBody.createByError("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponseBody.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponseBody<String> register(User user) {
        ServerResponseBody<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponseBody.createByErrorMessage("注册失败");
        }
        return ServerResponseBody.createBySuccessMessage("注册成功");
    }

    /**
     * 功能：当用户在注册页面时，
     *
     * @param str  用户名也可以是email
     * @param type type是username和email
     * @return 返回服务响应对象
     */
    @Override
    public ServerResponseBody<String> checkValid(String str, String type) {
        if (StringUtils.isNoneBlank(type)) {
            int resultCount;
            if (Const.EMAIL.equals(type)) {
                resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponseBody.createByErrorMessage("email已存在");
                }
            }
            if (Const.USERNAME.equals(type)) {
                resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponseBody.createByErrorMessage("用户已存在");
                }
            }
        } else {
            return ServerResponseBody.createByErrorMessage("参数错误");
        }
        return ServerResponseBody.createBySuccessMessage("校验成功");
    }
}
