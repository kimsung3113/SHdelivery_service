package com.delivery.api.domain.store.controller.model;

import com.delivery.db.store.enums.StoreCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank   // "", " ", null
    private String address;

    @NotNull    // Enum으로 맵핑했기 때문에 문자로 볼 수 없어 notnull 이다.
    private StoreCategory storeCategory;

    @NotBlank
    private String thumbnailUrl;

    @NotNull    // 객체 타입이라 NotNull
    private BigDecimal minimumAmount;

    @NotNull
    private BigDecimal minimumDeliveryAmount;

    @NotBlank
    private String phoneNumber;

}
