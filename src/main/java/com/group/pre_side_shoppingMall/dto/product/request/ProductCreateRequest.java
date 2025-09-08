package com.group.pre_side_shoppingMall.dto.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수입니다")
    private String productName;

    @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다")
    private int productPrice;

    @PositiveOrZero(message = "상품 재고는 0개 이상이어야 합니다")
    private int productStock;
}
