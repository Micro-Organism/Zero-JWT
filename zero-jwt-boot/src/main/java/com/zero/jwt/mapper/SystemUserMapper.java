package com.zero.jwt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.jwt.domain.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface SystemUserMapper extends BaseMapper<SystemUserEntity> {

    @Select("SELECT * FROM system_user where username=#{username}")
    SystemUserEntity findByUsername(@Param("username") String username);

    @Select("SELECT * FROM system_user WHERE id = #{Id}")
    SystemUserEntity findUserById(@Param("Id") String Id);

}
