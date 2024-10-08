package com.delivery.storefranchise.domain.storeuser.controller;

import com.delivery.storefranchise.domain.storeuser.business.StoreUserBusiness;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserRegisterRequest;
import com.delivery.storefranchise.domain.storeuser.controller.model.StoreUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-api/store-user")
@RequiredArgsConstructor
public class StoreUserOpenApiController {

    private final StoreUserBusiness storeUserBusiness;

    @PostMapping("")
    public StoreUserResponse register(
            @Valid
            @RequestBody StoreUserRegisterRequest request
            ){
        var response = storeUserBusiness.register(request);
        return response;
    }


}
