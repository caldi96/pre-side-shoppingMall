package com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.request;

import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums.DeliveryStatus;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.enums.ParcelCorp;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCreateRequest {

    @NotNull(message = "배송지는 필수입니다")
    private String arrival;

    @NotNull(message = "배송상태는 필수입니다")
    private DeliveryStatus deliveryStatus;

    @NotNull(message = "택배사는 필수입니다")
    private ParcelCorp parcelCorp;

    @NotNull(message = "상품 ID는 필수입니다")
    @Min(value = 1, message = "상품 항목 ID는 1 이상이어야 합니다")
    private Long orderItemId;

    @NotNull(message = "유저 ID는 필수입니다")
    @Min(value = 1, message = "유저 ID는 1 이상이어야 합니다")
    private Long userId;
}
