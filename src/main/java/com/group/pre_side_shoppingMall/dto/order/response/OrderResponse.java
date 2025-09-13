package com.group.pre_side_shoppingMall.dto.order.response;

import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.domain.order.payment.Payment;
import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.dto.order.orderItem.response.OrderItemResponse;
import com.group.pre_side_shoppingMall.dto.order.payment.response.PaymentResponse;
import com.group.pre_side_shoppingMall.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderId;
    private UserResponse user;
    private int totalPrice;
    private List<OrderItemResponse> orderItems;
    private PaymentResponse payment;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.user = new UserResponse(order.getUser());
        this.totalPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList());
        this.payment = new PaymentResponse(order.getPayment());
    }
}
