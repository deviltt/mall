package com.mall.service;

import com.mall.common.ServerResponseBody;
import com.mall.pojo.User;

public interface IUserService {
    ServerResponseBody<User> login(String username, String password);
}
