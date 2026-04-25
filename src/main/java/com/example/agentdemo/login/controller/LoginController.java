package com.example.agentdemo.login.controller;

import com.example.agentdemo.common.Result;
import com.example.agentdemo.login.Service.LoginService;
import com.example.agentdemo.login.common.Dto.LoginDto;
import com.example.agentdemo.login.common.Dto.RegisterDto;
import com.example.agentdemo.login.common.Vo.LoginVo;
import com.example.agentdemo.login.common.Vo.RegisterVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = loginService.login(loginDto);
        return Result.success(loginVo);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto){
        loginService.register(registerDto);
        return Result.success();
    }

}
