package com.group.pre_side_shoppingMall.domain.product;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId = null;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int productPrice;

    private int productStock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}
