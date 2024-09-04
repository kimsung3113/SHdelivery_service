package com.delivery.storefranchise.domain.storeuser.controller;

import com.delivery.storefranchise.domain.authorization.model.UserSession;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserResponse;
import com.delivery.storefranchise.domain.storeuser.converter.StoreUserConverter;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-user")
@RequiredArgsConstructor
public class StoreUserApiController {

    private final StoreUserConverter storeUserConverter;

    @GetMapping("/me")
    public StoreUserResponse me(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        return storeUserConverter.toResponse(userSession);
    }

}
