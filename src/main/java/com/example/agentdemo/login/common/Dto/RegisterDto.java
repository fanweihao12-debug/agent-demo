package com.example.agentdemo.login.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RegisterDto implements Serializable {
    public String name;
    public String passWord;
    public String phoneNumber;
    public Integer age;
    public String email;
    @JsonProperty("imageUrl")
    public String image;
}
