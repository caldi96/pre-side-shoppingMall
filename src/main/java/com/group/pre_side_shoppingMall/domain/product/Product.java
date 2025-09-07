package com.group.pre_side_shoppingMall.domain.product;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.dto.product.request.ProductPriceUpdateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductStockUpdateRequest;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId = null;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private int productPrice;

    private int productStock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Product() {}

    public Product(String productName, int productPrice, int productStock) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("제품명이 비었습니다");
        }
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void updateProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void updateProductStock(int productStock) {
        this.productStock = productStock;
    }
}
