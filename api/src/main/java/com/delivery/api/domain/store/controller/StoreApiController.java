package com.delivery.api.domain.store.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.store.business.StoreBusiness;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.db.store.enums.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // TODO 가맹점 서버에서 통신을 해 삭제할 수 있게 or Status 값 설정하여 Unregistered로?
    @DeleteMapping("/storeId/{storeId}")
    public void deleteStore(@PathVariable Long storeId){

        storeBusiness.deleteStore(storeId);

    }

}
