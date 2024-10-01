package com.delivery.api.domain.store.business;

import com.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.common.annotation.Business;
import com.delivery.db.store.enums.StoreCategory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest storeRegisterRequest){

        // req -> entity -> response
        var entity = storeConverter.toEntity(storeRegisterRequest);
        var registeredEntity = storeService.register(entity);
        var response = storeConverter.toResponse(registeredEntity);
        return response;

    }

    public List<StoreResponse> searchCategory(StoreCategory storeCategory){

        var storeList = storeService.searchByCategory(storeCategory);

        return storeList.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());

    }

    public void deleteStore(Long id){

        // store 있는지 확인 or exception
        var storeEntity = storeService.getStoreWithThrow(id);

        // store 삭제
        storeService.deleteStore(id);

    }

}
