package com.group.pre_side_shoppingMall.domain.order.payment.enums;

public enum PaymentWay {
    CREDIT_CARD("신용카드"),
    DEBIT_CARD("체크카드"),
    BANK_TRANSFER("계좌이체"),
    KAKAO_PAY("카카오페이"),
    NAVER_PAY("네이버페이"),
    PAYCO("페이코");

    private final String description;

    PaymentWay(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
