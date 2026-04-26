package com.example.agentdemo.login.Service;

import java.time.Duration;

public interface StoreRefreshTokenService {

    /**
     * 存储刷新令牌白名单记录。
     *
     * @param userId  用户 ID。
     * @param tokenId 刷新令牌 ID（jti）。
     * @param ttl     生存时间（过期后自动失效）。
     */
    void storeToken(long userId, String tokenId, Duration ttl);

    /**
     * 校验刷新令牌是否仍然有效（在白名单内且未过期）。
     *
     * @param userId  用户 ID。
     * @param token 刷新令牌 。
     * @return 是否有效。
     */
    boolean isTokenValid(long userId, String token);

    /**
     * 撤销指定刷新令牌（从白名单移除）。
     *
     * @param userId  用户 ID。
     * @param token 刷新令牌。
     */
    void revokeToken(long userId, String token);

    /**
     * 撤销用户的所有刷新令牌（强制该用户所有会话下线）。
     *
     * @param userId 用户 ID。
     */
    void revokeAll(long userId);

    /**
     * 建立用户id与refreshToken的对应关系
     * @param userId
     * @param refreshToken
     * @param refreshTtl
     */
    void storeUserAndToken(long userId, String refreshToken, Duration refreshTtl);

    /**
     * 获得refreshtoken
     * @param userId
     * @return
     */
    String getRefreshToken(Long userId);

    /**
     * 刷新token
     * @param userId
     * @param token
     */
    void refreshToken(Long userId, String token,String type);
}
