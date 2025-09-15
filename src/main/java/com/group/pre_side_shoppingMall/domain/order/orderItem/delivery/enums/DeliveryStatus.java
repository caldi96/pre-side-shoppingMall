package com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums;

public enum DeliveryStatus {
    READY("배송 준비 중"),
    SHIPPING("배송 중"),
    DELIVERED("배송 완료");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
