package com.delivery.storefranchise.domain.authorization.service;

import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.storefranchise.domain.authorization.converter.UserSessionConverter;
import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.store.service.StoreService;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final UserSessionConverter userSessionConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userSessionConverter.toUserSession(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

    }

}
