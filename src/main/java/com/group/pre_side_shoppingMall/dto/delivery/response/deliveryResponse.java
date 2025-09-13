package com.group.pre_side_shoppingMall.dto.delivery.response;

import com.group.pre_side_shoppingMall.domain.delivery.enums.DeliveryStatus;
import com.group.pre_side_shoppingMall.domain.delivery.enums.ParcelCorp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class deliveryResponse {

    private Long deliveryId;
    private String parcelNumber;
    private String arrival;
    private DeliveryStatus deliveryStatus;
    private ParcelCorp parcelCorp;
    private Long orderItemId;
    private Long productId;
    private String productName;
    private String quantity;
    private Long userId;
    private String name;
}
