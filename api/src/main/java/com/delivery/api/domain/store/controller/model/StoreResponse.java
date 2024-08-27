package com.delivery.api.domain.store.controller.model;

import com.delivery.db.store.enums.StoreCategory;
import com.delivery.db.store.enums.StoreStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {

    private Long id;

    private String name;

    private String address;

    private StoreStatus status;

    private StoreCategory category;

    private double star;

    private String thumbnailUrl;

    private BigDecimal minimumAmount;

    private BigDecimal minimumDeliveryAmount;

    private String phoneNumber;


}
