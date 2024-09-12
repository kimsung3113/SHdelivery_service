package com.delivery.storefranchise.domain.store.service;

import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // TODO Store 이름으로 찾아오니깐 나중에 refactoring 해야될듯(Unique도 아님)
    public Optional<StoreEntity> getRegisteredStore(String storeName, StoreStatus storeStatus){
        return storeRepository.findFirstByNameAndStatusOrderByIdDesc(storeName, storeStatus);
    }

    /* StoreId로 찾아오기*/
    public Optional<StoreEntity> getRegisteredStore(Long storeId, StoreStatus storeStatus){
        return storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeId, storeStatus);
    }

}
