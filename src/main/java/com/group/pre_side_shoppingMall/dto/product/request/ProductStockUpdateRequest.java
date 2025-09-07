package com.group.pre_side_shoppingMall.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockUpdateRequest {

    private Long productId;
    private int productStock;
}
