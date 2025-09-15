package com.group.pre_side_shoppingMall.domain.order;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    Optional<Order> findByUserAndOrderId(User user, Long orderId);

    Optional<Order> findByOrderItem(OrderItem orderItem);
}
