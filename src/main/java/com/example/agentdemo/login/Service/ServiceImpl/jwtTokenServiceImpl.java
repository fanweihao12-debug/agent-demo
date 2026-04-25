package com.example.agentdemo.login.Service.ServiceImpl;

import com.example.agentdemo.common.Entity.User;
import com.example.agentdemo.login.Config.jwtProperties;
import com.example.agentdemo.login.Service.StoreRefreshTokenService;
import com.example.agentdemo.login.Service.jwtTokenService;
import com.example.agentdemo.login.common.TokenPair;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class jwtTokenServiceImpl implements jwtTokenService {

    @Resource
    private JwtEncoder jwtEncoder;

    @Resource
    private JwtDecoder jwtDecoder;

    @Resource
    private jwtProperties jwtProperties;

    @Resource
    private StoreRefreshTokenService redisStore;


    @Override
    public TokenPair createAndStoreToken(User user) {
        String refreshTokenId = UUID.randomUUID().toString();
        String accessTokenId = UUID.randomUUID().toString();
        Instant now = Instant.now();
        Instant refreshTokenExpireTime = now.plus(jwtProperties.getRefreshTokenExpire());
        Instant accessTokenExpireTime = now.plus(jwtProperties.getAccessTokenExpire());

        String refreshToken = encodeToken(user,now,refreshTokenExpireTime,"refresh",refreshTokenId);
        String accessToken = encodeToken(user,now,accessTokenExpireTime,"access",accessTokenId);

        TokenPair tokenPair = TokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .refreshTokenId(refreshTokenId)
                .accessTokenId(accessTokenId)
                .build();

        storeRedis(tokenPair,user.getId());

        return tokenPair;
    }

    /**
     * 将token存入redis中
     * @param tokenPair
     */
    private void storeRedis(TokenPair tokenPair,long userId) {
        Duration refreshTtl = Duration.between(Instant.now(), tokenPair.getRefreshTokenExpireTime());
        Duration accessTtl = Duration.between(Instant.now(),tokenPair.getAccessTokenExpireTime());

        redisStore.storeToken(userId,tokenPair.getRefreshTokenId(),refreshTtl);
        redisStore.storeToken(userId,tokenPair.getAccessTokenId(),accessTtl);
    }


    /**
     * 编码访问令牌。
     *
     * @param user      用户实体，作为 subject 与自定义声明来源。
     * @param issuedAt  签发时间。
     * @param expiresAt 过期时间。
     * @param tokenType 令牌类型（"access"）。
     * @param tokenId   令牌 ID（jti）。
     * @return 编码后的 JWT 字符串。
     */
    private String encodeToken(User user, Instant issuedAt, Instant expiresAt, String tokenType, String tokenId) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(String.valueOf(user.getId()))
                .id(tokenId)
                .claim("token_type", tokenType)
                .claim("name", user.getName())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * 解码访问令牌
     * @param token
     * @return
     */
    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }
}
