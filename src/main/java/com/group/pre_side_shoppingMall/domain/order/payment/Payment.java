package com.group.pre_side_shoppingMall.domain.order.payment;

import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentWay;
import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId = null;

    @Enumerated(EnumType.STRING)
    private PaymentWay paymentWay;

    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne
    private Order order;
}
