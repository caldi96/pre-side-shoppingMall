package com.group.pre_side_shoppingMall.dto.order.response;

import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.domain.order.payment.Payment;
import com.group.pre_side_shoppingMall.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderId;
    private User user;
    private int totalPrice;
    private List<OrderItem> orderItems;
    private Payment payment;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.user = order.getUser();
        this.totalPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems();
        this.payment = order.getPayment();
    }
}
