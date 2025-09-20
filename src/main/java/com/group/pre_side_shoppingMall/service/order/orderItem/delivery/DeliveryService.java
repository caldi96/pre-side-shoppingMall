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
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.request.DeliveryUpdateRequest;
import com.group.pre_side_shoppingMall.dto.order.orderItem.delivery.response.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final TrackingNumberGenerator trackingNumberGenerator;

    // 택배사 및 배송상태 생성
    @Transactional
    public DeliveryResponse createDelivery(DeliveryCreateRequest request) {

        // 1. 유저확인
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다"));

        // 2. 해당 유저의 주문 확인
        Order order = orderRepository.findByUser(user).stream()
                .filter(o -> o.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 건이 존재하지 않습니다"));

        // 3. 주문 아이템 확인
        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getOrderItemId().equals(request.getOrderItemId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 주문 항목이 존재하지 않습니다"));

                /*
        // 3. 주문 아이템 확인
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 항목이 없습니다"));
                 */

        // 4. 해당 배송 건이 이미 등록된 건인지 확인
        if (deliveryRepository.existsByOrderItem(orderItem)) {
            throw new IllegalArgumentException("이미 존재하는 배송건입니다");
        }

        // 5. 결제 내역
        Payment payment = order.getPayment();

        PaymentStatus paymentStatus = payment.getPaymentStatus();

        Delivery savedDelivery = null;

        // 6. paymentStatus가 COMPLETED 일때만 배송 생성
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

    // 해당 상품 항목의 배송정보 가져오기
    @Transactional
    public DeliveryResponse getDelivery(Long orderItemId) {

        // 1. 상품 항목 존재 유무 확인
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 항목이 존재하지 않습니다"));

        // 2. 배송정보 확인
        Delivery delivery = deliveryRepository.findByOrderItem(orderItem)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 항목의 배송정보가 존재하지 않습니다"));

        return new DeliveryResponse(delivery);
    }

    // 해당 상품 항목의 배송정보 변경
    @Transactional
    public void updateDeliveryStatus(DeliveryUpdateRequest request) {

        // 1. 배송 정보 확인
        Delivery delivery = deliveryRepository.findById(request.getDeliveryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 배송정보가 존재하지 않습니다"));

        if (delivery.getDeliveryStatus().equals(request.getDeliveryStatus())) {
            throw new IllegalArgumentException("현재 배송상태와 동일하게 지정할 수는 없습니다");
        }

        delivery.updateDeliveryStatus(request.getDeliveryStatus());
    }
}

