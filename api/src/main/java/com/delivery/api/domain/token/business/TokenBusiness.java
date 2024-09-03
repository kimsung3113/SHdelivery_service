package com.delivery.api.domain.token.business;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.token.converter.TokenConverter;
import com.delivery.api.domain.token.service.TokenService;
import com.delivery.common.annotation.Business;
import com.delivery.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /*
    *  1. user entity user Id 추출
    *  2. access, refresh token 발행
    *  3. converter -> tokenResponse로 변경
    * */
    public TokenResponse issueToken(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    return ue.getId();
                })
                .map(userId ->{
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(
                        ()-> new ApiException(ErrorCode.NULL_POINT)
                );

    }

    public Long validationAccessToken(String accessToken){
       var userId = tokenService.validationToken(accessToken);
       return userId;
    }

}
