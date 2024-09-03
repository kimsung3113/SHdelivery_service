package com.delivery.api.domain.user.business;

import com.delivery.api.domain.token.business.TokenBusiness;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.user.controller.model.UserLoginRequest;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.converter.UserConverter;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.user.service.UserService;
import com.delivery.common.annotation.Business;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;
    /*
    *  사용자에 대한 가입처리 로직
    *  1. request -> entity
    *  2. entity -> save
    *  3. save entity -> response
    *  4. response return
    * */
    public UserResponse register(UserRegisterRequest request) {

        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;

        /*return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
        .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request is null"));*/

    }

    /*
    * 1. email, password를 가지고 사용자 체크
    * 2. user entity 로그인 확인
    * 3. token 생성
    * 4. token response
    * */
    public TokenResponse login(UserLoginRequest request) {

        var userEntity = userService.login(request.getEmail(), request.getPassword());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;

    }

    public UserResponse me(User user) {
        var response = userConverter.toResponse(user);
        return response;
    }
}
