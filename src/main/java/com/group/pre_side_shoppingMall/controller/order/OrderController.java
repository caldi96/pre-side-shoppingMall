package com.group.pre_side_shoppingMall.controller.order;

import com.group.pre_side_shoppingMall.dto.order.request.OrderCreateRequest;
import com.group.pre_side_shoppingMall.dto.order.response.OrderResponse;
import com.group.pre_side_shoppingMall.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문생성
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
