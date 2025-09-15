package com.group.pre_side_shoppingMall.service.order.orderItem.delivery;

import com.group.pre_side_shoppingMall.common.TrackingNumberGenerator;
import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItem;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.Delivery;
import com.group.pre_side_shoppingMall.domain.order.orderItem.delivery.DeliveryRepository;
import com.group.pre_side_shoppingMall.domain.order.OrderRepository;
import com.group.pre_side_shoppingMall.domain.order.orderItem.OrderItemRepository;
import com.group.pre_side_shoppingMall.domain.order.payment.Payment;
import com.group.pre_side_shoppingMall.domain.order.payment.PaymentRepository;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.domain.user.UserRepository;
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.request.DeliveryCreateRequest;
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.response.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final TrackingNumberGenerator trackingNumberGenerator;

    // 택배사 및 배송상태 생성
    @Transactional
    public DeliveryResponse createDelivery(DeliveryCreateRequest request) {

        // 1. 주문 아이템 확인
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 항목이 없습니다"));

        // 2. 주문
        Order order = orderItem.getOrder();

        // 3. 결제 내역
        Payment payment = order.getPayment();

        PaymentStatus paymentStatus = payment.getPaymentStatus();

        Delivery savedDelivery = null;

        // paymentStatus가 COMPLETED 일때만 배송 생성
        if (paymentStatus == PaymentStatus.COMPLETED) {
            // 운송장 번호 생성
            String parcelNumber = trackingNumberGenerator.generateTrackingNumber();

            Delivery delivery = new Delivery(request, orderItem, parcelNumber);

            savedDelivery = deliveryRepository.save(delivery);
        } else {
            throw new IllegalArgumentException("해당 주문의 결제가 완료되지 않았습니다");
        }

        DeliveryResponse response = new DeliveryResponse(savedDelivery);

        return response;
    }
}

