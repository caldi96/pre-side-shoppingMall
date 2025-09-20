package com.group.pre_side_shoppingMall.controller.order.orderItem.delivery;

import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.request.DeliveryCreateRequest;
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.request.DeliveryUpdateRequest;
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.response.DeliveryResponse;
import com.group.pre_side_shoppingMall.service.order.orderItem.delivery.DeliveryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 생성
    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody @Valid DeliveryCreateRequest request) {
        DeliveryResponse response = deliveryService.createDelivery(request);
        return ResponseEntity.ok(response);
    }

    // 배송 정보 조회
    @GetMapping("/{orderItemId}")
    public ResponseEntity<DeliveryResponse> getDelivery(
            @PathVariable
            @Min(value = 1, message = "상품 항목 Id는 1 이상의 값이어야 합니다")
            Long orderItemId) {
        DeliveryResponse response = deliveryService.getDelivery(orderItemId);
        return ResponseEntity.ok(response);
    }

    // 배송 상태 변경
    @PutMapping
    public void updateDeliveryStatus(@RequestBody @Valid DeliveryUpdateRequest request) {
        deliveryService.updateDeliveryStatus(request);
    }
}
