package com.delivery.storefranchise.domain.authorization.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserSessionConverter {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    public Optional<UserSession> toUserSession(String email){

        var storeUserEntity = storeUserService.getRegisterUser(email);

        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
                storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);

        var userSession = storeUserEntity.map(it -> {

            var user = UserSession.builder()
                    .userId(it.getId())
                    .password(it.getPassword())
                    .email(it.getEmail())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .refreshToken(it.getRefreshToken())
                    .registeredAt(it.getRegisteredAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .lastLoginAt(it.getLastLoginAt())

                    .storeId(storeEntity.get().getId())
                    .storeName(storeEntity.get().getName())
                    .build();
            return user;
        });

        return userSession;
    }
}



