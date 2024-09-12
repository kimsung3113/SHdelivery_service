package com.delivery.db.storeuser;

import com.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    // SELECT * FROM store_user WHERE email =? AND status =? ORDER BY id DESC limit 1;
    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);

    boolean existsByEmail(String email);

    Optional<StoreUserEntity> findByRefreshToken(String refreshToken);

}
