package com.delivery.db.userordermenu;

import com.delivery.db.BaseEntity;
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@Entity
@Table(name = "user_order_menu")
public class UserOrderMenuEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userOrderId;   // 1 : N

    @Column(nullable = false)
    private Long storeMenuId;  // 1 : N

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserOrderMenuStatus status;

}
