package com.example.agentdemo.login.common.Vo;

import java.time.Instant;

public record TokenResponse(
        String refreshToken,
        String accessToken,
        Instant refreshTokenExpireTime,
        Instant accessTokenExpireTime
) {
}
