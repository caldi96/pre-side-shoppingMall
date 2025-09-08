package com.group.pre_side_shoppingMall.dto.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceUpdateRequest {

    @Min(value = 1, message = "상품 ID는 1 이상의 값이어야 합니다.")
    private Long productId;

    @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다")
    private int productPrice;
}
