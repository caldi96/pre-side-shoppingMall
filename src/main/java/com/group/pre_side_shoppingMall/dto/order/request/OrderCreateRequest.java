package com.group.pre_side_shoppingMall.dto.order.request;

import com.group.pre_side_shoppingMall.dto.order.orderItem.request.OrderItemRequest;
import com.group.pre_side_shoppingMall.dto.order.payment.request.PaymentRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "사용자 ID는 필수입니다")
    @Min(value = 1, message = "사용자 ID는 1 이상이어야 합니다")
    private Long userId; // 주문자

    // @PositiveOrZero(message = "전체 가격은 0 이상이어야 합니다")
    // private int totalPrice; // 전체 가격

    @NotEmpty(message = "주문 상품은 최소 1개 이상이어야 합니다")
    @Valid
    private List<OrderItemRequest> orderItems;

    @NotNull(message = "결제 정보는 필수입니다")
    @Valid
    private PaymentRequest payment;
}
