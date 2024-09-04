package com.delivery.storefranchise.domain.userorder.controller.model;

import com.delivery.storefranchise.domain.storemenu.controller.model.StoreMenuResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse;
    private List<StoreMenuResponse> storeMenuResponseList;


}
