package com.delivery.storefranchise.domain.storeuser.business;

import com.delivery.common.annotation.Business;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.storefranchise.domain.store.service.StoreService;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserRegisterRequest;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserResponse;
import com.delivery.storefranchise.domain.storeuser.converter.StoreUserConverter;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;

    private final StoreService storeService;

    private final PasswordEncoder passwordEncoder;

    // TODO NullCheck 필요

    public StoreUserResponse register(
        StoreUserRegisterRequest request
    ){
        System.out.println("StoreUserRegisterRequest 값 들어오는지 확인 : " + request.toString());

        var storeEntity = storeService.getRegisteredStore(request.getStoreName(), StoreStatus.REGISTERED);

        System.out.println(storeEntity.get().getName());
        var entity = storeUserConverter.toEntity(request, storeEntity.get());

        // 여기서 Encode후 register 메서드로 save
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        var newEntity = storeUserService.register(entity);

        var response = storeUserConverter.toResponse(newEntity, storeEntity.get());
        return response;
    }

}
