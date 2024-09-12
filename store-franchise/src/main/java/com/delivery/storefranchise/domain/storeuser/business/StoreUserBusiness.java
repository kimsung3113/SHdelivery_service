package com.delivery.storefranchise.domain.storeuser.business;

import com.delivery.common.annotation.Business;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.storefranchise.domain.store.service.StoreService;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserRegisterRequest;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserResponse;
import com.delivery.storefranchise.domain.storeuser.converter.StoreUserConverter;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;

    private final StoreService storeService;

    public StoreUserResponse register(
        StoreUserRegisterRequest request
    ){
        var storeEntity = storeService.getRegisteredStore(request.getStoreName(), StoreStatus.REGISTERED);

        var entity = storeUserConverter.toEntity(request, storeEntity.get());

        var newEntity = storeUserService.register(entity);

        var response = storeUserConverter.toResponse(newEntity, storeEntity.get());
        return response;
    }

}
