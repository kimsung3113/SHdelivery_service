package com.delivery.db.userorder;

import com.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    // 특정 유저의 모든 주문
    // SELECT * FROM user_order WHERE user_id =? And status =? Order By id Desc;
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    // SELECT * FROM user_order WHERE user_id =? And status In(?, ? ..) Order By id Desc;
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

    // 특정주문
    // SELECT * FROM user_order WHERE id =? And user_id = ? And status =? ;
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

    // Select * FROM user_order WHERE id = ? And user_id = ?;
    Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);

}
