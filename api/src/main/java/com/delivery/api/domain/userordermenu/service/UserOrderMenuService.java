package com.delivery.api.domain.userordermenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.delivery.db.userordermenu.UserOrderMenuRepository;
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    // Null 체크를 안하는 이유는 DB의 컬럼 값이 null일수도 있기때문, 없으면 select가 안된다.
    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId){
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(
                userOrderId,
                UserOrderMenuStatus.REGISTERED
        );
    }

    // user_order_menu 테이블에 데이터 삽입.
    public UserOrderMenuEntity order(UserOrderMenuEntity userOrderMenuEntity){
        return Optional.ofNullable(userOrderMenuEntity)
                .map(it -> {
                    it.setStatus(UserOrderMenuStatus.REGISTERED);
                    return userOrderMenuRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


}
