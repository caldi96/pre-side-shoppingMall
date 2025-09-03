package com.group.pre_side_shoppingMall.domain.delivery;

import com.group.pre_side_shoppingMall.domain.delivery.enums.DeliveryStatus;
import com.group.pre_side_shoppingMall.domain.delivery.enums.ParcelCorp;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import jakarta.persistence.*;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId = null;

    private String parcelNumber;

    private String arrival;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Enumerated(EnumType.STRING)
    private ParcelCorp parcelCorp;

    @OneToOne
    private OrderItem orderItem;
}
