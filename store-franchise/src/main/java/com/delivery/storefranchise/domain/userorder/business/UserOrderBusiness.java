package com.delivery.storefranchise.domain.userorder.business;

import com.delivery.common.annotation.Business;
import com.delivery.common.message.model.UserOrderMessage;
import com.delivery.storefranchise.domain.sse.connection.SseConnectionPool;
import com.delivery.storefranchise.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.storefranchise.domain.storemenu.service.StoreMenuService;
import com.delivery.storefranchise.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.storefranchise.domain.userorder.converter.UserOrderConverter;
import com.delivery.storefranchise.domain.userorder.service.UserOrderService;
import com.delivery.storefranchise.domain.userordermenu.service.UserOrderMenuService;
import lombok.RequiredArgsConstructor;


@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    private final SseConnectionPool sseConnectionPool;

    /*
    *   주문
    *   주문 내역 찾기
    *   스토어 찾기
    *   연결된 세션 찾아서
    *   push
    * */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {

        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(() -> new RuntimeException("User order not found"));


        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu -> storeMenuResponse
        var storeMenuList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                })
                .map(storeMenuEntity -> {
                    return storeMenuConverter.toResponse(storeMenuEntity);
                })
                .toList();

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response
        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuList)
                .build();

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 push
        userConnection.sendMessage(push);
    }

}
