package com.delivery.storefranchise.domain.authorization.handler;


import com.delivery.storefranchise.domain.authorization.ifs.JwtTokenIfs;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final StoreUserService storeUserService;
    private final JwtTokenIfs jwtTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,  Authentication authentication) throws IOException, ServletException {

        System.out.println("LoginSuccessJWTProvideHandler onAuthenticationSuccess메서드");

        String email = extractEmail(authentication);
        String accessToken = jwtTokenService.createAccessToken(email);
        String refreshToken = jwtTokenService.createRefreshToken();

        jwtTokenService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        storeUserService.getRegisterUser(email).ifPresent(
                storeUserEntity -> storeUserEntity.updateRefreshToken(refreshToken)
        );

        log.info( "로그인에 성공합니다. email: {}" , email);
        log.info( "AccessToken 을 발급합니다. AccessToken: {}" ,accessToken);
        log.info( "RefreshToken 을 발급합니다. RefreshToken: {}" ,refreshToken);

        response.getWriter().write("success");
    }

    private String extractEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
