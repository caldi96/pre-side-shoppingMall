package com.group.pre_side_shoppingMall.dto.delivery.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class deliveryCreateRequest {

    @NotNull(message = "배송지는 필수입니다")
    private String arrival;

    @NotNull(message = "상품 ID는 필수입니다")
    @Min(value = 1, message = "상품 항목 ID는 1 이상이어야 합니다")
    private Long orderItemId;
}
