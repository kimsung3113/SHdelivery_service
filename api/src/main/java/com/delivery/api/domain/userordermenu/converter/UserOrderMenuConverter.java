package com.delivery.api.domain.userordermenu.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.db.userordermenu.UserOrderMenuEntity;

@Converter
public class UserOrderMenuConverter {

    public UserOrderMenuEntity toEntity(
            UserOrderEntity userOrderEntity,
            StoreMenuEntity storeMenuEntity
    ){
        return UserOrderMenuEntity.builder()
                .userOrderId(userOrderEntity.getId())
                .storeMenuId(storeMenuEntity.getId())
                .build();
    }



}
