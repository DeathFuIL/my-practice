package com.example.mapper;

import com.example.dto.request.UserRequest;
import com.example.dto.response.UserResponse;
import com.example.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequest userRequest);

    UserResponse toResponse(UserEntity userEntity);
}
