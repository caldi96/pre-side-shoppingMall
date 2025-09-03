package com.group.pre_side_shoppingMall.domain.order.payment.enums;

public enum PaymentStatus {
    READY("결제 대기"),
    COMPLETED("결제 완료"),
    CANCELLED("결제 취소"),
    FAILED("결제 실패"),
    REFUNDED("환불 완료");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
