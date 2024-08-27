package com.delivery.api.domain.userorder.controller.model;

import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse;

    private StoreResponse storeResponse;

    private List<StoreMenuResponse> storeMenuResponseList;

}
