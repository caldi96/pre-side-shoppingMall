package com.group.pre_side_shoppingMall.dto.order.payment.response;

import com.group.pre_side_shoppingMall.domain.order.payment.Payment;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentWay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long paymentId = null;
    private PaymentWay paymentWay;
    private int paymentPrice;
    private PaymentStatus paymentStatus;

    public PaymentResponse(Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.paymentWay = payment.getPaymentWay();
        this.paymentPrice = payment.getPaymentPrice();
        this.paymentStatus = payment.getPaymentStatus();
    }
}
