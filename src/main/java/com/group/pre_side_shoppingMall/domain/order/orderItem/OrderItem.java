package com.group.pre_side_shoppingMall.domain.order.orderItem;

import com.group.pre_side_shoppingMall.domain.delivery.Delivery;
import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId = null;

    private int quantity;

    private int orderPrice;

    @ManyToOne
    @JoinColumn(name = "product_id") // 실제 DB 컬럼명
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id") // DB 컬럼과 매핑
    private Order order;

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Delivery delivery;
}
