package com.group.pre_side_shoppingMall.dto.order.orderItem.response;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Long productId;
    //private String productName;
    private int quantity;
    private int orderPrice;

    public OrderItemResponse(OrderItem orderItem) {
        this.productId = orderItem.getOrderItemId();
        //this.productName = productName;
        this.quantity = orderItem.getQuantity();
        this.orderPrice = orderItem.getOrderPrice();
    }
}
