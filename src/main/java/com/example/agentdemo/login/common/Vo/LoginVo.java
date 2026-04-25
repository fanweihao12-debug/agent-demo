package com.example.agentdemo.login.common.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    UserResponse userResponse;
    TokenResponse tokenResponse;
}
