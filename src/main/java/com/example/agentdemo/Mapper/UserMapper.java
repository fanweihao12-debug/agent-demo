package com.example.agentdemo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.agentdemo.common.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
