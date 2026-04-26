package com.example.agentdemo.login.Service.ServiceImpl;

import com.example.agentdemo.login.Config.jwtProperties;
import com.example.agentdemo.login.Service.StoreRefreshTokenService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
public class RedisStoreRefreshToken implements StoreRefreshTokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private jwtProperties jwtProperties;

    @Resource
    private JwtDecoder jwtDecoder;

    @Override
    public void storeToken(long userId, String tokenId, Duration ttl) {
        String key = key(userId,tokenId);
        redisTemplate.opsForValue().set(key,"1",ttl);
    }

    @Override
    public boolean isTokenValid(long userId, String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String tokenId = jwt.getId();
        String key = key(userId,tokenId);
        return "1".equals(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void revokeToken(long userId, String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String tokenId = jwt.getId();
        String key = key(userId,tokenId);
        redisTemplate.delete(key);
    }

    @Override
    public void revokeAll(long userId) {
        String key ="jwt:token:%d:*".formatted(userId);
        Set<String> keys = redisTemplate.keys(key);
        if(keys.isEmpty()){
            return;
        }
        redisTemplate.delete(keys);
        key = "User:refreshToken:"+userId;
        redisTemplate.delete(key);
    }

    @Override
    public void storeUserAndToken(long userId, String refreshToken, Duration refreshTtl) {
        String key = "User:refreshToken:"+userId;
        redisTemplate.opsForValue().set(key,refreshToken,refreshTtl);
    }

    @Override
    public String getRefreshToken(Long userId) {
        String key = "User:refreshToken:"+userId;
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void refreshToken(Long userId, String token,String type) {
        Jwt jwt = jwtDecoder.decode(token);
        String tokenId = jwt.getId();
        String key = key(userId,tokenId);
        Duration ttl = "access".equals(type)?jwtProperties.getAccessTokenExpire():jwtProperties.getRefreshTokenExpire();
        redisTemplate.opsForValue().set(key,"1",ttl);
    }

    /**
     * 生成白名单键名。
     *
     * @param userId  用户 ID。
     * @param tokenId 刷新令牌 ID。
     * @return Redis 键名。
     */
    private static String key(long userId, String tokenId) {
        return "jwt:token:%d:%s".formatted(userId, tokenId);
    }
}
