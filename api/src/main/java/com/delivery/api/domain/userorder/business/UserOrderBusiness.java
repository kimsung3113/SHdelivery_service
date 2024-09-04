package com.delivery.api.domain.userorder.business;

import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.api.domain.userorder.converter.UserOrderConverter;
import com.delivery.api.domain.userorder.producer.UserOrderProducer;
import com.delivery.api.domain.userorder.service.UserOrderService;
import com.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import com.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import com.delivery.common.annotation.Business;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    private final UserOrderProducer userOrderProducer;

    // 1. 사용자, 메뉴 id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest request) {

        var storeMenuEntityList = request.getStoreMenuIdList()
                .stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .toList();

        var userOrderEntity = userOrderConverter.toEntity(user, request.getStoreId(), storeMenuEntityList);

        // 주문
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        //매핑
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // menu + userOrder
                    var userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                    return userOrderMenuEntity;
                })
                . toList();

        // 주문내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
                    userOrderMenuService.order(it);
        });

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);

    }

    public List<UserOrderDetailResponse> current(User user) {

        var userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(userOrderEntity -> {

            // 사용자가 주문한 하나의 상점에서 주문한 Menu List
            // 배민도 상점 기준으로 하는 것 확인.
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

            var storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity -> {
                        var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                        return storeMenuEntity;
                    })
                    .toList();

            // 사용자가 주문한 스토어
            // TODO refactoring 필요 - NullPoint Exception 터질 확률 있음.
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).toList();

        return userOrderDetailResponseList;

    }

    // TODO 데이터가 많이 나올수도 있으니 Paging 처리 필요할듯.
    public List<UserOrderDetailResponse> history(User user) {

        var userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(userOrderEntity -> {

            // 사용자가 주문한 하나의 상점에서 주문한 Menu List
            // 배민도 상점 기준으로 하는 것 확인.
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

            var storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity -> {
                        var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                        return storeMenuEntity;
                    })
                    .toList();

            // 사용자가 주문한 스토어
            // TODO refactoring 필요 - NullPoint Exception 터질 확률 있음.
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).toList();

        return userOrderDetailResponseList;

    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());

        // 사용자가 주문한 하나의 상점에서 주문한 Menu List
        // 배민도 상점 기준으로 하는 것 확인.
        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .toList();

        // 사용자가 주문한 스토어
        // TODO refactoring 필요 - NullPoint Exception 터질 확률 있음.
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .build();
    }
}
