package com.group.pre_side_shoppingMall.domain.order.orderItem;

import com.group.pre_side_shoppingMall.domain.delivery.Delivery;
import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.product.Product;
import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId = null;

    private int quantity;

    private int orderPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Delivery delivery;
}
