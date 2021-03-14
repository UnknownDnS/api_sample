package com.unknown.api.dto;

import com.unknown.api.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    String userId;
    String name;
    String password;

    public static UserDto fromEntity(UserEntity user){
        UserDto dto = new UserDto();
        dto.userId = user.getUserId();
        dto.name = user.getUserId();
        dto.password = user.getPassword();
        return dto;
    }
}
