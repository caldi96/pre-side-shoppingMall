package com.group.pre_side_shoppingMall.dto.order.orderItem.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "상품 ID는 필수입니다")
    @Min(value = 1, message = "상품 ID는 1 이상이어야 합니다")
    private Long productId;

    //@PositiveOrZero(message = "전체 가격은 0 이상이어야 합니다")
    //private int productPrice;

    @Min(value = 1, message = "주문 수량은 최소 1개 이상이어야 합니다")
    private int quantity;
}
