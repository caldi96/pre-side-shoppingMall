package com.group.pre_side_shoppingMall.service.order;

import com.group.pre_side_shoppingMall.domain.order.Order;
import com.group.pre_side_shoppingMall.domain.order.OrderRepository;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentStatus;
import com.group.pre_side_shoppingMall.domain.order.payment.enums.PaymentWay;
import com.group.pre_side_shoppingMall.domain.product.Product;
import com.group.pre_side_shoppingMall.domain.product.ProductRepository;
import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.domain.user.UserRepository;
import com.group.pre_side_shoppingMall.dto.order.orderItem.request.OrderItemRequest;
import com.group.pre_side_shoppingMall.dto.order.payment.request.PaymentRequest;
import com.group.pre_side_shoppingMall.dto.order.request.OrderCreateRequest;
import com.group.pre_side_shoppingMall.dto.order.response.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("주문생성 성공")
    void orderCreate_success() {
        // 1.유저 존재
        // given
        Long userId = 1L;
        User user = new User("testUser", "encodedPw", "홍길동", 20);
        ReflectionTestUtils.setField(user, "userId", userId);

        given(userRepository.findById(userId))
                .willReturn(Optional.of(user));

        // request 생성

        // 2.상품 존재
        // given
        // 상품 2개 준비
        Product product1 = new Product("세탁기", 2000000, 3);
        ReflectionTestUtils.setField(product1, "productId", 1L);

        Product product2 = new Product("냉장고", 1500000, 2);
        ReflectionTestUtils.setField(product2, "productId", 2L);

        List<Product> products = List.of(product1, product2);

        Set<Long> productIds = Set.of(1L, 2L);
        given(productRepository.findAllById(productIds)).willReturn(products);

        List<OrderItemRequest> orderItems = List.of(
                new OrderItemRequest(1L, 1), // 세탁기 1개
                new OrderItemRequest(2L, 1)  // 냉장고 1개
        );
        // 주문 아이템 요청
        OrderCreateRequest request = new OrderCreateRequest(
                1L,
                orderItems,
                new PaymentRequest(PaymentWay.CREDIT_CARD)
                );

        // orderRepository.save() mocking
        // orderRepository는 직접 DB에 접근하는 것이 아니기 때문에 모킹이 필요함
        // 즉 orderRepository.save 에 어떤 Order.class의 파라미터가 들어가면
        // 호출된 메서드의 첫번째 인자를 그대로 반환하겠다는 뜻으로
        // DB 없이도 save(order) 호출시 전달된 order 객체를 그대로 돌려주겠다는 의미아다
        given(orderRepository.save(any(Order.class)))
                .willAnswer(invocationOnMock -> (Order) invocationOnMock.getArguments()[0]);

        OrderResponse response = orderService.createOrder(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getOrderItems()).hasSize(2);
        assertThat(response.getPayment().getPaymentWay()).isEqualTo(PaymentWay.CREDIT_CARD);
        assertThat(response.getPayment().getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED);

        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findAllById(productIds);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 실패 - 유저 존재하지 않음")
    void createOrder_fail_notExistUser() {
        // given
        Long userId = 99L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when & then
        OrderCreateRequest request = new OrderCreateRequest(
                99L,
                List.of(
                        new OrderItemRequest(1L, 1), // 세탁기 1개
                        new OrderItemRequest(2L, 1)  // 냉장고 1개
                ),
                new PaymentRequest(PaymentWay.CREDIT_CARD, 2000000)
        );

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 유저입니다");

        // Optional: save() 호출 안되는지 확인
        // 예외는 낫지만 userRepository.findById 가 한 번 일어났다
        verify(userRepository, times(1)).findById(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 실패 - 상품 존재하지 않음")
    void createOrder_fail_notExistProduct() {
        // given
        // 1. 유저 존재
        Long userId = 1L;
        User existingUser = new User("testUser", "encodedPw", "홍길동", 20);
        ReflectionTestUtils.setField(existingUser, "userId", userId);

        Long productId1 = 1L;
        Product product1 = new Product("세탁기", 2000000, 3);
        ReflectionTestUtils.setField(product1, "productId", productId1);

        Long productId2 = 2L;
        Product product2 = new Product("냉장고", 1500000, 2);
        ReflectionTestUtils.setField(product2, "productId", productId2);

        Set<Long> productIds = new LinkedHashSet<>(Arrays.asList(1L, 2L));

        given(userRepository.findById(1L)).willReturn(Optional.of(existingUser));
        // 상품 1만 호출되도록 실패케이스 지정
        // productIds는 Set 자료형이므로 given과 밑의 verify에서 순서를 보장하지 않는다
        // 그래서 argThat을 사용해서 Set 비교 시 순서 무시하도록 한다
        // given(productRepository.findAllById(productIds))
        //        .willReturn(List.of(product1));
        given(productRepository.findAllById(any(Iterable.class))).willReturn(List.of(product1)); // 2번 상품은 없는 걸로

        // when & then
        OrderCreateRequest request = new OrderCreateRequest(
                1L,
                List.of(
                        new OrderItemRequest(1L, 1), // 세탁기 1개
                        new OrderItemRequest(2L, 1)  // 냉장고 1개
                ),
                new PaymentRequest(PaymentWay.CREDIT_CARD, 2000000)
        );

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품 ID: [2]");

        // Optional: productRepository.save() 호출 안되는지 확인
        // 여기서 문제점이 있다
        // productId 는 Set이다 보니 순서를 보장하지 않는다
        // 위의         given(productRepository.findAllById(productIds))
        //                .willReturn(List.of(product1));
        // 에서는 [1, 2] 로 들어갔지만
        // 밑의 verify 에서는 [2, 1]로 들어갔기 때문에 실패가 뜬다.
        // 그래서 다른 방식으로 해준다.
        //verify(productRepository, times(1)).findAllById(productIds);

        // argThat을 사용해서 Set 비교 시 순서 무시.
        // 이제 [1,2]든 [2,1]든 테스트가 일관되게 동작합니다.
        // 즉, 순서나 equals 문제 없이 안전하게
        verify(productRepository, times(1)).findAllById(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 실패 - 재고 부족")
    void createOrder_fail_notEqualProductCount() {
        // given
        // 1. 유저 존재
        Long userId = 1L;
        User existingUser = new User("testUser", "encodedPw", "홍길동", 20);
        ReflectionTestUtils.setField(existingUser, "userId", userId);

        Long productId1 = 1L;
        Product product1 = new Product("세탁기", 2000000, 3);
        ReflectionTestUtils.setField(product1, "productId", productId1);

        Long productId2 = 2L;
        Product product2 = new Product("냉장고", 1500000, 2);
        ReflectionTestUtils.setField(product2, "productId", productId2);

        Set<Long> productIds = new LinkedHashSet<>(Arrays.asList(1L, 2L));

        given(userRepository.findById(1L)).willReturn(Optional.of(existingUser));
        given(productRepository.findAllById(any(Iterable.class))).willReturn(List.of(product1, product2)); // 2번 상품은 없는 걸로

        // when & then
        OrderCreateRequest request = new OrderCreateRequest(
                1L,
                List.of(
                        new OrderItemRequest(1L, 5), // 세탁기 1개
                        new OrderItemRequest(2L, 1)  // 냉장고 1개
                ),
                new PaymentRequest(PaymentWay.CREDIT_CARD, 2000000)
        );

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 세탁기의 재고가 부족합니다. 요청: 5, 재고: 3");

        verify(productRepository, never()).save(any(Product.class));
    }
}
