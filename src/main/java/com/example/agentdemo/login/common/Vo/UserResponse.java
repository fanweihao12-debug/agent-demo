package com.example.agentdemo.login.common.Vo;

public record UserResponse(
         Long id,
         String name,
         String passWord,
         Integer age,
         String phoneNumber
) {
}
