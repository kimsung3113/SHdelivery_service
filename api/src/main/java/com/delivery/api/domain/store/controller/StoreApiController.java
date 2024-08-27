package com.delivery.api.domain.store.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.store.business.StoreBusiness;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.db.store.enums.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<List<StoreResponse>> search(
            @RequestParam(required = false)     // 필수 값은 아니다.
            StoreCategory storeCategory
    ){
        var response = storeBusiness.searchCategory(storeCategory);
        return Api.OK(response);
    }

}
