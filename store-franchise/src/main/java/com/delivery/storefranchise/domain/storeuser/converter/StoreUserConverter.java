package com.delivery.storefranchise.domain.storeuser.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserRegisterRequest;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Converter
@RequiredArgsConstructor
public class StoreUserConverter {

    private final PasswordEncoder passwordEncoder;

    public StoreUserEntity toEntity(
            StoreUserRegisterRequest request,
            StoreEntity storeEntity
    ){
        var storeName = request.getStoreName();

        // Business에서 Converting 할 때 encode한 Password 집어넣는다.
        return StoreUserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .storeId(storeEntity.getId())     // TODO NULL일때 에러체크 확인 필요
                .build();
    }

    public StoreUserResponse toResponse(
        StoreUserEntity storeUserEntity,
        StoreEntity storeEntity
    ){
        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                                .id(storeUserEntity.getId())
                                .email(storeUserEntity.getEmail())
                                .status(storeUserEntity.getStatus())
                                .role(storeUserEntity.getRole())
                                .registeredAt(storeUserEntity.getRegisteredAt())
                                .unregisteredAt(storeUserEntity.getUnregisteredAt())
                                .lastLoginAt(storeUserEntity.getLastLoginAt())
                                .build()
                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                                .id(storeEntity.getId())
                                .name(storeEntity.getName())
                                .build()
                )
                .build();
    }

    public StoreUserResponse toResponse(
            UserSession userSession
    ){
        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                                .id(userSession.getUserId())
                                .email(userSession.getEmail())
                                .status(userSession.getStatus())
                                .role(userSession.getRole())
                                .registeredAt(userSession.getRegisteredAt())
                                .unregisteredAt(userSession.getUnregisteredAt())
                                .lastLoginAt(userSession.getLastLoginAt())
                                .build()
                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                                .id(userSession.getStoreId())
                                .name(userSession.getStoreName())
                                .build()
                )
                .build();
    }


}
