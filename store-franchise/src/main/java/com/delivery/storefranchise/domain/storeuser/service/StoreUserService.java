package com.delivery.storefranchise.domain.storeuser.service;

import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.db.storeuser.StoreUserRepository;
import com.delivery.db.storeuser.enums.StoreUserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;  // Bean으로 Config에 설정해 주입 할 수 있다.

    public StoreUserEntity register(
        StoreUserEntity storeUserEntity
    ){
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        // 인코딩한 password 저장.
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        storeUserEntity.setRegisteredAt(LocalDateTime.now());

        return storeUserRepository.save(storeUserEntity);
    }

    public Optional<StoreUserEntity> getRegisterUser(String email){
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }
}
