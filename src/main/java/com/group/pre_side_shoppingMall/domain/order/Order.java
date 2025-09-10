package com.group.pre_side_shoppingMall.domain.order;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.domain.order.payment.Payment;
import com.group.pre_side_shoppingMall.domain.product.Product;
import com.group.pre_side_shoppingMall.domain.user.User;
import jakarta.persistence.*;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId = null;

    @ManyToOne
    @JoinColumn(name = "user_id") // DB 컬럼 이름과 정확히 맞춤
    private User user;

    private int totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    protected Order() {}

    public Order(User user, int totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void createOrderItem(int quantity, int orderPrice, Product product) {
        orderItems.add(new OrderItem(quantity, orderPrice, product, this));
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        // Payment 에 orderId 추가
        // 이렇게 order와 payment 간 양방향 매핑을 한다
        payment.setOrder(this);
    }
}
