package com.example.agentdemo.login.Service;

import com.example.agentdemo.common.Entity.User;
import com.example.agentdemo.login.common.TokenPair;


public interface jwtTokenService {
    /**
     * 用户登录时，生成两个token并存入redis中。
     *
     * @param user
     * @return accesstoken
     */
    TokenPair createAndStoreToken(User user);
}
