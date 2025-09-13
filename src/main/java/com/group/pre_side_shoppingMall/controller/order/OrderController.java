package com.group.pre_side_shoppingMall.controller.order;

import com.group.pre_side_shoppingMall.dto.order.request.OrderCreateRequest;
import com.group.pre_side_shoppingMall.dto.order.response.OrderResponse;
import com.group.pre_side_shoppingMall.service.order.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 해당 유저의 전체 주문 목록 가져오기(다건)
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrders(
            @PathVariable
            @Min(value = 1, message = "유저 Id는 1 이상의 값이어야 합니다")
            Long userId) {
        List<OrderResponse> responses = orderService.getOrders(userId);
        return ResponseEntity.ok(responses);
    }

    // 해당 유저의 주문건 가져오기(단건)
    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable
            @Min(value = 1, message = "유저 Id는 1 이상의 값이어야 합니다")
            Long userId,
            @PathVariable
            @NotNull
            @Min(value = 1, message = "상품 Id는 1 이상의 값이어야 합니다")
            Long orderId) {
        OrderResponse response = orderService.getOrder(userId, orderId);
        return ResponseEntity.ok(response);
    }
}
