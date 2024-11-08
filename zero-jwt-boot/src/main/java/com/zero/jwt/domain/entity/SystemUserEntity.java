package com.zero.jwt.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//简单自定义一个实体类User
//使用lombok简化实体类的编写
public class SystemUserEntity {
    String Id;
    String username;
    String password;
}
