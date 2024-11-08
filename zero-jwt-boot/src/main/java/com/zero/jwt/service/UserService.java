package com.zero.jwt.service;

import com.zero.jwt.domain.entity.SystemUserEntity;
import com.zero.jwt.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UserService")
public class UserService {

    @Autowired
    SystemUserMapper userMapper;

    public SystemUserEntity findByUsername(SystemUserEntity user) {
        return userMapper.findByUsername(user.getUsername());
    }

    public SystemUserEntity findUserById(String userId) {
        return userMapper.findUserById(userId);
    }

}
