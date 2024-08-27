package com.delivery.api.domain.storemenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.storemenu.StoreMenuRepository;
import com.delivery.db.storemenu.enums.StoreMenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;


    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        var entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(
                id,
                StoreMenuStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeID){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeID, StoreMenuStatus.REGISTERED);
    }

    public StoreMenuEntity register(StoreMenuEntity storeMenuEntity) {
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> {
                    it.setStatus(StoreMenuStatus.REGISTERED);
                    return storeMenuRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

}
