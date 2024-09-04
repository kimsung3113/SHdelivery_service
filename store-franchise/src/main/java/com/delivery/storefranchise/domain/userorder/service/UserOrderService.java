package com.delivery.storefranchise.domain.userorder.service;

import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.db.userorder.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public Optional<UserOrderEntity> getUserOrder(long id){
        return userOrderRepository.findById(id);
    }

}
