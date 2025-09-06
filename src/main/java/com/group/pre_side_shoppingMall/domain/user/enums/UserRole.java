package com.group.pre_side_shoppingMall.domain.user.enums;

public enum UserRole {
    ADMIN("관리자"),
    USER("일반회원");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
