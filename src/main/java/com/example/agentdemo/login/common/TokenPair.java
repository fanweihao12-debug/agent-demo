package com.example.agentdemo.login.common;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TokenPair {
    public String accessToken;
    public String refreshToken;
    public Instant accessTokenExpireTime;
    public Instant refreshTokenExpireTime;
    public String refreshTokenId;
    public String accessTokenId;
}
