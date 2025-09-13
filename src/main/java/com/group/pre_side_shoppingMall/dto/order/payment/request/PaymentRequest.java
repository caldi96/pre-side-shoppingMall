package com.group.pre_side_shoppingMall.dto.order.payment.request;

import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentWay;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotNull(message = "결제 방식은 필수입니다")
    private PaymentWay paymentWay;

    @PositiveOrZero(message = "상품 가격은 0 이상이어야 합니다")
    private int paymentPrice;

    //@NotNull(message = "결제 상태값은 필수 입니다")
    //private PaymentStatus paymentStatus;


    public PaymentRequest(PaymentWay paymentWay) {
        this.paymentWay = paymentWay;
    }
}
