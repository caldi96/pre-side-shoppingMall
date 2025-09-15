package com.group.pre_side_shoppingMall.domain.order.orderItem.delivery;

import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums.DeliveryStatus;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums.ParcelCorp;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.request.DeliveryCreateRequest;
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

    protected Delivery() {}

    public Delivery(DeliveryCreateRequest request, OrderItem orderItem ,String parcelNumber) {
        this.orderItem = orderItem;
        this.parcelNumber = parcelNumber;
        this.arrival = request.getArrival();
        this.parcelCorp = request.getParcelCorp();
        this.deliveryStatus = DeliveryStatus.READY;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public String getParcelNumber() {
        return parcelNumber;
    }

    public String getArrival() {
        return arrival;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public ParcelCorp getParcelCorp() {
        return parcelCorp;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }
}
