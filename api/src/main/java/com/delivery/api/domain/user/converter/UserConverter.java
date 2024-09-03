package com.delivery.api.domain.user.converter;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.model.User;
import com.delivery.common.annotation.Converter;
import com.delivery.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request){

        return Optional.ofNullable(request)
                .map(it -> {
                    // to entity
                   return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .address(request.getAddress())
                            .build();
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest is Null"));
    }


    public UserResponse toResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(it -> {
                    // to response
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .status(userEntity.getStatus())
                            .email(userEntity.getEmail())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();

                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity is Null"));

    }

    // RequestContext에서 entity 가져올때 에러 throw를 해서 여기에는 null체크를 안했다.
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .status(user.getStatus())
                .email(user.getEmail())
                .address(user.getAddress())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }


}
