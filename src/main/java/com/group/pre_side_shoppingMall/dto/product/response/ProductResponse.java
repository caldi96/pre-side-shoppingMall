package com.group.pre_side_shoppingMall.dto.product.response;

import com.group.pre_side_shoppingMall.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long productId;
    private String productName;
    private int productPrice;
    private int productStock;

    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productStock = product.getProductStock();
    }
}
