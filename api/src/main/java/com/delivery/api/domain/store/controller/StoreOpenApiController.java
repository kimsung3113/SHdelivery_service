package com.delivery.api.domain.store.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.store.business.StoreBusiness;
import com.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store")
public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    // 로그인 한 사람이 하는 것이 아닌 가맹점 직원이 등록한다.
    @PostMapping("/register")
    public Api<StoreResponse> register(
            @Valid
            @RequestBody Api<StoreRegisterRequest> request
    ){
        var response = storeBusiness.register(request.getBody());
        return Api.OK(response);
    }

}
