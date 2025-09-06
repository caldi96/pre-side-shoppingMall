package com.group.pre_side_shoppingMall.service.product;

import com.group.pre_side_shoppingMall.domain.product.Product;
import com.group.pre_side_shoppingMall.domain.product.ProductRepository;
import com.group.pre_side_shoppingMall.dto.product.request.ProductCreateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductGetRequest;
import com.group.pre_side_shoppingMall.dto.product.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("상품등록 성공")
    void productCreate_success() {
        // given
        ProductCreateRequest request = new ProductCreateRequest("testProduct", 1000, 50);
        Product product = new Product("testProduct", 1000, 50);

        given(productRepository.findByProductName("testProduct")).willReturn(Optional.empty());
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response.getProductName()).isEqualTo("testProduct");
        assertThat(response.getProductPrice()).isEqualTo(1000);
        assertThat(response.getProductStock()).isEqualTo(50);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품등록 실패 - 이미 존재하는 상품")
    void create_fail_duplicateProductName() {
        // given
        Product existingProduct = new Product("testProduct", 1000, 50);
        ProductCreateRequest request = new ProductCreateRequest("testProduct", 1000, 50);

        given(productRepository.findByProductName("testProduct")).willReturn(Optional.of(existingProduct));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("상품정보 전체 리스트 가져오기")
    void getProducts_success() {
        Product product1 = new Product("상품1", 1000, 5);
        Product product2 = new Product("상품2", 2000, 3);

        given(productRepository.findAll()).willReturn(Arrays.asList(product1, product2));

        List<ProductResponse> responses = productService.getProducts();

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getProductName()).isEqualTo("상품1");
        assertThat(responses.get(1).getProductName()).isEqualTo("상품2");
    }

    @Test
    @DisplayName("상품정보 가져오기(단건) 성공")
    void getProduct_success() {
        // given
        ProductGetRequest request = new ProductGetRequest(1L);
        Product product = new Product("냉장고", 2000000, 3);

        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        ProductResponse response = productService.getProduct(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getProductName()).isEqualTo("냉장고");
        assertThat(response.getProductPrice()).isEqualTo(2000000);
        assertThat(response.getProductStock()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품이 존재하지 않으면 예외를 던진다")
    void getProduct_fail_notFound() {
        // given
        ProductGetRequest request = new ProductGetRequest(99L);
        given(productRepository.findById(99L)).willReturn(Optional.empty());

        // when & then
//        assertThrows(IllegalArgumentException.class, () -> productService.getProduct(request));
        assertThatThrownBy(() -> productService.getProduct(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다");

    }
}
