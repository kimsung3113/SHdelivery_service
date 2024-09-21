package com.delivery.storefranchise.domain.authorization.service;

import com.delivery.storefranchise.domain.authorization.converter.UserSessionConverter;
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
        System.out.println("AuthorizationService loadUserByUsername 메서드 실행");
        return userSessionConverter.toUserSession(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

    }

}
