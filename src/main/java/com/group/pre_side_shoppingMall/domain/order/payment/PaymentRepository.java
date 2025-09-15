package com.group.pre_side_shoppingMall.domain.order.payment;

import com.group.pre_side_shoppingMall.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);
}
