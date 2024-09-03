package com.delivery.api.domain.token.converter;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.token.model.TokenDto;
import com.delivery.common.annotation.Converter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class TokenConverter {

    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreshToken
    ){
        // Null 체크
        Objects.requireNonNull(accessToken, () ->{throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, () ->{throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }

}
