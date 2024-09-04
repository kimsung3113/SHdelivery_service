package com.delivery.storefranchise.domain.storemenu.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.storefranchise.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StoreMenuConverter {

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity){
        return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId())
                .name(storeMenuEntity.getName())
                .status(storeMenuEntity.getStatus())
                .amount(storeMenuEntity.getAmount())
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                .likeCount(storeMenuEntity.getLikeCount())
                .sequence(storeMenuEntity.getSequence())
                .build();
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list){
        return list.stream()
                .map(it -> {
                    return toResponse(it);
                }).collect(Collectors.toList());
    }

}
