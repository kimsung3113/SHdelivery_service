package com.delivery.storefranchise.domain.userorder.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.storefranchise.domain.userorder.controller.model.UserOrderResponse;

@Converter
public class UserOrderConverter {

    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity){
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId())
                .userId(userOrderEntity.getUserId())
                .storeId(userOrderEntity.getStoreId())
                .status(userOrderEntity.getStatus())
                .amount(userOrderEntity.getAmount())
                .orderedAt(userOrderEntity.getOrderedAt())
                .acceptedAt(userOrderEntity.getAcceptedAt())
                .cookingStartedAt(userOrderEntity.getCookingStartedAt())
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
                .receivedAt(userOrderEntity.getReceivedAt())
                .build();
    }


}
