package com.delivery.storefranchise.domain.authorization.filter;

import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storefranchise.domain.authorization.converter.UserSessionConverter;
import com.delivery.storefranchise.domain.authorization.ifs.JwtTokenIfs;
import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final StoreUserService storeUserService;
    private final JwtTokenIfs jwtTokenService;
    private final UserSessionConverter userSessionConverter;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();//5

    // /open-api/auth/login, /open-api/store-user
    private final String NO_CHECK_URL = "/open-api/auth/login";//1

    /**
     * 1. 리프레시 토큰이 오는 경우 -> 유효하면 AccessToken 재발급후, 필터 진행 X, 바로 튕기기
     *
     * 2. 리프레시 토큰은 없고 AccessToken만 있는 경우 -> 유저정보 저장후 필터 계속 진행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(NO_CHECK_URL)) {
            System.out.println("JwtAuthenticationProcessingFilter doFilterInternal메서드");
            filterChain.doFilter(request, response);
            return;//안해주면 아래로 내려가서 계속 필터를 진행하게됨
        }

        String refreshToken = jwtTokenService
                .extractRefreshToken(request)
                .filter(jwtTokenService::isTokenValid)
                .orElse(null); //2


        if(refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);//3
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);//4
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtTokenService.extractAccessToken(request).filter(jwtTokenService::isTokenValid).ifPresent(

                accessToken -> jwtTokenService.extractEmail(accessToken).ifPresent(

                        email -> storeUserService.getRegisterUser(email).ifPresent(

                                storeUserEntity -> saveAuthentication(storeUserEntity)
                        )
                )
        );

        filterChain.doFilter(request,response);
    }



    private void saveAuthentication(StoreUserEntity storeUserEntity) {

        // TODO 여기서 UserSession 객체 만들어 줘야 된다. Converter나 여기 Null check 코드롤 넣거나 해야될 듯.
        UserSession userDetails = userSessionConverter.toUserSession(storeUserEntity.getEmail()).get();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();//5
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {

        System.out.println("refresh Token : " + refreshToken);

        storeUserService.getRegisterUserToRefreshToken(refreshToken).ifPresent(
                storeUserEntity -> jwtTokenService.sendAccessToken(response, jwtTokenService.createAccessToken(storeUserEntity.getEmail()))
        );


    }
}