package com.group.pre_side_shoppingMall.service.order;

import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.OrderRepository;
import com.group.pre_side_shoppingMall.domain.order.payment.Payment;
import com.group.pre_side_shoppingMall.domain.product.Product;
import com.group.pre_side_shoppingMall.domain.product.ProductRepository;
import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.domain.user.UserRepository;
import com.group.pre_side_shoppingMall.dto.order.orderItem.request.OrderItemRequest;
import com.group.pre_side_shoppingMall.dto.order.request.OrderCreateRequest;
import com.group.pre_side_shoppingMall.dto.order.response.OrderResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {

        // 1.유저 확인
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));

        // 2.상품 존재 유무 확인

        // 요청에서 넘어온 상품 ID
        Set<Long> productIds = request.getOrderItems().stream()
                .map(OrderItemRequest::getProductId)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // DB 조회 -> 요청에서 받아온 상품 ID들로 DB에서 상품들을 가져옴
        List<Product> products = productRepository.findAllById(productIds);

        // 요청한 상품 개수와 DB에서 조회된 개수가 다르면 → 존재하지 않는 상품 있음
        if (products.size() != productIds.size()) {
            Set<Long> foundsId = products.stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toSet());
            productIds.removeAll(foundsId);
            throw new IllegalArgumentException("존재하지 않는 상품 ID: " + productIds);
        }

        /*
        // 하나라도 null 값이 있으면 참이다. 하지만 애초에 null 값을 productRepository 에서 돌려주지 않는다.
        boolean anyInvalid = StreamSupport.stream(products.spliterator(), false)
                .anyMatch(product -> product == null);

        if (!anyInvalid) {
            throw new IllegalArgumentException("존재하지 않는 상품이 포함되어 있습니다");
        }
         */

        // 3.재고 확인 - 요청한 상품들의 수량보다 남아있는 재고가 같거나 큰지 확인

        // 요청에서 넘어온 상품 수량
        Map<Long, Integer> requestedQuantitiesMap = request.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItemRequest::getProductId, OrderItemRequest::getQuantity));

        // 요청한 상품 종류별 수량이 DB에서 조회된 상품 재고보다 많으면 → 재고 부족
        /*
        for (Product product : products) {
            int requestedQuantity = requestedQuantitiesMap.get(product.getProductId());

            if (product.getProductStock() < requestedQuantity) {
                throw new IllegalArgumentException(String.format("상품 %s의 재고가 부족합니다. 현재 상품 %s의 재고는 %d개 입니다."
                        , product.getProductName(), product.getProductName(), product.getProductStock()));
            }
        }
         */

        // stream으로 재고 확인
        // 하나의 상품이라도 재고가 부족하면 예외 던짐
        /*
        // 방식1
        boolean insufficientStock = products.stream()
                .anyMatch(product -> product.getProductStock() <
                        requestedQuantitiesMap.get(product.getProductId()));

        if (insufficientStock) {
            throw new IllegalArgumentException("재고가 부족한 상품이 있습니다");
        }
         */
        // 방식2
        products.stream()
                .filter(product -> product.getProductStock() < requestedQuantitiesMap.get(product.getProductId()))
                .findFirst()
                .ifPresent(product -> {
                    throw new IllegalArgumentException(
                            String.format("상품 %s의 재고가 부족합니다. 요청: %d, 재고: %d",
                                    product.getProductName(),
                                    requestedQuantitiesMap.get(product.getProductId()),
                                    product.getProductStock())
                    );
                });

        // 4.전체 가격 계산
        /*
        // 1) for문으로 계산
        int totalPrice = 0;
        for (Product product : products) {
            int quantity = requestedQuantitiesMap.get(product.getProductId());
            totalPrice += product.getProductPrice() * quantity;
        }
         */
        // 2) stream으로 계산
        int totalPrice = products.stream()
                .mapToInt(product -> product.getProductPrice() * requestedQuantitiesMap.get(product.getProductId()))
                .sum();

        // 5.재고 차감
        // 1) for문으로 처리
        /*
        for (Product product : products) {
            product.minusProductStock(requestedQuantitiesMap.get(product.getProductId()));
        }
         */
        // 2) forEach로 처리
        products.stream()
                .forEach(product ->
                        product.minusProductStock(requestedQuantitiesMap.get(product.getProductId()))
                );

        // 6.주문 생성
        Order order = new Order(user, totalPrice);

        // 7. 주문 아이템 생성
        // 1) for문으로 계산
        for (Product product : products) {
            int quantity = requestedQuantitiesMap.get(product.getProductId());
            int orderPrice = product.getProductPrice() * quantity;
            order.createOrderItem(quantity, orderPrice, product);
        }
        // 2) stream으로 계산
        /*
        products.stream()
                .forEach(product -> {
                    int quantity = requestedQuantitiesMap.get(product.getProductId());
                    int orderPrice = product.getProductPrice() * quantity;
                    order.createOrderItem(quantity, orderPrice, product);
                });
         */

        // 8. 결제
        Payment payment = new Payment(
                request.getPayment().getPaymentWay(),
                totalPrice
        );
        order.setPayment(payment);

        // 9. 저장 -> 주문 생성
        Order savedOrder = orderRepository.save(order);

        // 외부 결제 api 호출 성공 시 상태변경
        // 여기서는 우선 외부 결제 api 호출이 성공했다고 가정
        payment.completePaymentStatus();

        return new OrderResponse(savedOrder);
    }

    @Transactional
    public List<OrderResponse> getOrders(Long userId) {

        // 1. 유저 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        List<Order> orders = orderRepository.findByUser(user);

        List<OrderResponse> responses = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return responses;
    }

    @Transactional
    public OrderResponse getOrder(Long userId, Long orderId) {

        // 1. 유저 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        // 2. 해당 주문이 존재하는지 확인
        Order order = orderRepository.findByUserAndOrderId(user ,orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 건이 존재하지 않습니다"));

        OrderResponse response = new OrderResponse(order);

        return response;
    }
}
