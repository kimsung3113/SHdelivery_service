package com.delivery.storefranchise.domain.user.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.user.controller.model.StoreUserRegisterRequest;
import com.delivery.storefranchise.domain.user.controller.model.StoreUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Converter
@RequiredArgsConstructor
public class StoreUserConverter {

    public StoreUserEntity toEntity(
            StoreUserRegisterRequest request,
            StoreEntity storeEntity
    ){
        var storeName = request.getStoreName();

        return StoreUserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
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
