package com.delivery.storefranchise.domain.user.business;

import com.delivery.common.annotation.Business;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.storefranchise.domain.user.controller.model.StoreUserRegisterRequest;
import com.delivery.storefranchise.domain.user.controller.model.StoreUserResponse;
import com.delivery.storefranchise.domain.user.converter.StoreUserConverter;
import com.delivery.storefranchise.domain.user.service.StoreUserService;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;

    private final StoreRepository storeRepository; // TODO SERVICE로 변경하기

    public StoreUserResponse register(
        StoreUserRegisterRequest request
    ){
        var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);

        var entity = storeUserConverter.toEntity(request, storeEntity.get());

        var newEntity = storeUserService.register(entity);

        var response = storeUserConverter.toResponse(newEntity, storeEntity.get());
        return response;
    }

}
