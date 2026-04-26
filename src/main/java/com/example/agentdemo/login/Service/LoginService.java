package com.example.agentdemo.login.Service;

import com.example.agentdemo.login.common.Dto.LoginDto;
import com.example.agentdemo.login.common.Dto.RegisterDto;
import com.example.agentdemo.login.common.Vo.LoginVo;


public interface LoginService {
    /**
     * 登录的具体逻辑
     * @param loginDto
     * @return
     */
    LoginVo login(LoginDto loginDto);

    /**
     * 注册用户
     *
     * @param registerDto
     */
    void register(RegisterDto registerDto);

    /**
     * 登出用户
     * @param userId
     */
    void logout(long userId);
}
