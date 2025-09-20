package com.group.pre_side_shoppingMall.domain.order.orderItem.delivery;

import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    boolean existsByOrderItem(OrderItem orderItem);

    Optional<Delivery> findByOrderItem(OrderItem orderItem);
}
