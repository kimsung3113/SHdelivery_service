package com.delivery.api.domain.storemenu.controller.model;

import com.delivery.db.storemenu.enums.StoreMenuStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreMenuResponse {

    private Long id;

    private Long storeId;

    private String name;

    private BigDecimal amount;

    private StoreMenuStatus status;

    private String thumbnailUrl;

    private int likeCount;

    private int sequence;

}
