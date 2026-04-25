package com.example.agentdemo.login.Config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.time.Duration;
import java.time.Instant;

@Data
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class jwtProperties {
    private Duration refreshTokenExpire = Duration.ofDays(7);

    private Duration accessTokenExpire = Duration.ofHours(1);

    private String issuer;

    private String keyId;
    private Resource privateKey;
    private Resource publicKey;
}
