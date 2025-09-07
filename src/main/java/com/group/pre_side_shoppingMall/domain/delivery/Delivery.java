package com.group.pre_side_shoppingMall.domain.delivery;

import com.group.pre_side_shoppingMall.domain.delivery.enums.DeliveryStatus;
import com.group.pre_side_shoppingMall.domain.delivery.enums.ParcelCorp;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import jakarta.persistence.*;

@Entity
@Table(name = "delivery")
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
    @JoinColumn(name = "order_item_id") // DB 컬럼과 매핑
    private OrderItem orderItem;
}
