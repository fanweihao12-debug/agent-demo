package com.example.agentdemo.login.common.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    private String name;

    private String passWord;
}
