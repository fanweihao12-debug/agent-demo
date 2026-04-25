package com.example.agentdemo.login.Config;

import com.example.agentdemo.Utils.PemUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class jwtConfig {

    @Resource
    private jwtProperties jwtProperties;


    @Bean
    public JwtEncoder jwtEncoder(){
        RSAPrivateKey rsaPrivateKey = PemUtils.readPrivateKey(jwtProperties.getPrivateKey());
        RSAPublicKey rsaPublicKey = PemUtils.readPublicKey(jwtProperties.getPublicKey());
        RSAKey jwk = new RSAKey.Builder(rsaPublicKey)
                .privateKey(rsaPrivateKey)
                .keyID(jwtProperties.getKeyId())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        RSAPublicKey publicKey = PemUtils.readPublicKey(jwtProperties.getPublicKey());
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}
