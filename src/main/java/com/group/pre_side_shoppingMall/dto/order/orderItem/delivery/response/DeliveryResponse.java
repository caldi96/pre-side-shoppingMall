package com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.response;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.Delivery;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums.DeliveryStatus;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums.ParcelCorp;
import com.group.pre_side_shoppingMall.domain.product.Product;
import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.dto.order.orderItem.response.OrderItemResponse;
import com.group.pre_side_shoppingMall.dto.product.response.ProductResponse;
import com.group.pre_side_shoppingMall.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {

    private Long deliveryId;
    private String parcelNumber;
    private String arrival;
    private DeliveryStatus deliveryStatus;
    private ParcelCorp parcelCorp;
    private OrderItemResponse orderItem;
    private ProductResponse product;
    private UserResponse user;

    public DeliveryResponse(Delivery delivery) {
        this.deliveryId = delivery.getDeliveryId();
        this.parcelNumber = delivery.getParcelNumber();
        this.arrival = delivery.getArrival();
        this.deliveryStatus = delivery.getDeliveryStatus();
        this.parcelCorp = delivery.getParcelCorp();
        this.orderItem = new OrderItemResponse(delivery.getOrderItem());
        this.product = new ProductResponse(delivery.getOrderItem().getProduct());
        this.user = new UserResponse(delivery.getOrderItem().getOrder().getUser());
    }
}
