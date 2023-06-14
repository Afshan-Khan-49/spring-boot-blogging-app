package com.example.bloggingapp.user.mapper;

import com.example.bloggingapp.user.dto.UserResponseDto;
import com.example.bloggingapp.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserResponseDto userToUserResponse(User user);
}
