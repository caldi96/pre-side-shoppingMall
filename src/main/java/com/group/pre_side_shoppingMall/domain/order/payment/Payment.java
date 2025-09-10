package com.group.pre_side_shoppingMall.domain.order.payment;

import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentWay;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId = null;

    @Enumerated(EnumType.STRING)
    private PaymentWay paymentWay;

    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Setter
    @OneToOne
    @JoinColumn(name = "order_id") // DB 컬럼과 매핑
    private Order order;

    protected Payment() {}

    public Payment(PaymentWay paymentWay, int paymentPrice) {
        this.paymentWay = paymentWay;
        this.paymentPrice = paymentPrice;
        this.paymentStatus = PaymentStatus.READY;
    }

    public void completePaymentStatus() {
        this.paymentStatus = PaymentStatus.COMPLETED;
    }

    public void canceledPaymentStatus() {
        this.paymentStatus = PaymentStatus.CANCELLED;
    }

    public void failedPaymentStatus() {
        this.paymentStatus = PaymentStatus.FAILED;
    }

    public void refundPaymentStatus() {
        this.paymentStatus = PaymentStatus.REFUNDED;
    }
}
