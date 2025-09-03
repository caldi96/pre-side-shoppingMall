package com.group.pre_side_shoppingMall.domain.delivery.enums;

public enum ParcelCorp {
    CJ("CJ 대한통운"),
    HANJIN("한진택배"),
    LOTTE("롯데택배"),
    LOGEN("로젠택배"),
    POST_OFFICE("우체국택배");

    private final String description;

    ParcelCorp(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
