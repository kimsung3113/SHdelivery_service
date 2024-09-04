package com.delivery.storefranchise.domain.storemenu.service;

import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.storemenu.StoreMenuRepository;
import com.delivery.db.storemenu.enums.StoreMenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id) {
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED)
                .orElseThrow(() -> new RuntimeException("User Menu Not Found"));
    }

}
