package com.example.agentdemo.login.Service.ServiceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.agentdemo.Mapper.UserMapper;
import com.example.agentdemo.common.Entity.User;
import com.example.agentdemo.login.Service.LoginService;
import com.example.agentdemo.login.Service.jwtTokenService;
import com.example.agentdemo.login.common.Dto.LoginDto;
import com.example.agentdemo.login.common.Dto.RegisterDto;
import com.example.agentdemo.login.common.TokenPair;
import com.example.agentdemo.login.common.Vo.LoginVo;
import com.example.agentdemo.login.common.Vo.TokenResponse;
import com.example.agentdemo.login.common.Vo.UserResponse;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private jwtTokenService jwtTokenService;

    @Override
    public LoginVo login(LoginDto loginDto) {
        String userName = loginDto.getName();
        String passWord = loginDto.getPassWord();
        passWord =  DigestUtils.md5DigestAsHex(passWord.getBytes());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", userName);
        User user = userMapper.selectOne(wrapper);
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
        if(!passWord.equals(user.getPassWord())){
            throw new RuntimeException("用户密码错误");
        }

        TokenPair tokenPair = jwtTokenService.createAndStoreToken(user);


        return mapIntoLoginVo(tokenPair,user);
    }

    @Override
    public void register(RegisterDto registerDto) {
        if(!StringUtils.hasText(registerDto.getName())){
            throw new RuntimeException("没有输入用户名");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",registerDto.getName());
        User user = userMapper.selectOne(wrapper);
        if(user!=null){
            throw new RuntimeException("用户名已存在，请重新输入用户名");
        }
        user = new User();
        BeanUtils.copyProperties(registerDto,user);
        String passWord = user.getPassWord();
        user.setPassWord(DigestUtils.md5DigestAsHex(passWord.getBytes()));
        userMapper.insert(user);
    }

    private LoginVo mapIntoLoginVo(TokenPair tokenPair, User user) {
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getPhoneNumber()
        );

        TokenResponse tokenResponse = new TokenResponse(
                tokenPair.getRefreshToken(),
                tokenPair.getAccessToken(),
                tokenPair.getRefreshTokenExpireTime(),
                tokenPair.getAccessTokenExpireTime()
        );

        return LoginVo.builder()
                .userResponse(userResponse)
                .tokenResponse(tokenResponse)
                .build();
    }
}
