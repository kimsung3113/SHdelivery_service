package com.delivery.storefranchise.domain.authorization;

import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.user.service.StoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;

    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var storeUserEntity = storeUserService.getRegisterUser(username);

        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
                storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);

        return storeUserEntity.map(it -> {

            var userSession = UserSession.builder()
                    .userId(it.getId())
                    .password(it.getPassword())
                    .email(it.getEmail())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .registeredAt(it.getRegisteredAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .lastLoginAt(it.getLastLoginAt())

                    .storeId(storeEntity.get().getId())
                    .storeName(storeEntity.get().getName())
                    .build();

            return userSession;
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
