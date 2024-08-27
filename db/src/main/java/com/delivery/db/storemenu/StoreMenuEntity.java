package com.delivery.db.storemenu;

import com.delivery.db.BaseEntity;
import com.delivery.db.storemenu.enums.StoreMenuStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@Entity
@Table(name = "store_menu")
public class StoreMenuEntity extends BaseEntity {

    @Column(nullable = false)
    private Long storeId;

    @Column(length = 100 ,nullable = false)
    private String name;

    @Column(precision = 11, scale = 4 ,nullable = false)
    private BigDecimal amount;

    @Column(length = 50 ,nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreMenuStatus status;

    @Column(length = 200 ,nullable = false)
    private String thumbnailUrl;

    // 아래 둘 다 기본값이 0이라 int형이다. null이었으면 Integer형으로 했다.
    private int likeCount;

    private int sequence;


}
