package com.example.agentdemo.common.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user") // 指定数据库表名
public class User {
    @TableId(type = IdType.AUTO) // 指定主键自增
    private Long id;
    private String name;
    private String passWord;
    private Integer age;
    private String phoneNumber;
    private String email;
    private String image;
}