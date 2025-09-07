package com.group.pre_side_shoppingMall.service.product;

import com.group.pre_side_shoppingMall.domain.product.Product;
import com.group.pre_side_shoppingMall.domain.product.ProductRepository;
import com.group.pre_side_shoppingMall.dto.product.request.ProductCreateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductGetRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductPriceUpdateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductStockUpdateRequest;
import com.group.pre_side_shoppingMall.dto.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품등록
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // 상품명 중복 체크
        productRepository.findByProductName(request.getProductName())
                .ifPresent(product -> {
                    throw new IllegalArgumentException(String.format("%s는 이미 존재하는 상품입니다.", product.getProductName()));
                });

        Product product = new Product(
                request.getProductName(),
                request.getProductPrice(),
                request.getProductStock()
        );

        Product savedProduct = productRepository.save(product);

        return new ProductResponse(savedProduct);
    }

    // 상품 전체 리스트 가져오기(다건)
    @Transactional
    public List<ProductResponse> getProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    // 상품정보 가져오기(단건)
    @Transactional
    public ProductResponse getProduct(ProductGetRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

        return new ProductResponse(product);
    }

    // 상품 가격 수정
    @Transactional
    public ProductResponse updateProductPrice(ProductPriceUpdateRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

        product.updateProductPrice(request.getProductPrice());

        return new ProductResponse(product);
    }

    // 상품 수량 수정
    @Transactional
    public ProductResponse updateProductStock(ProductStockUpdateRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

        product.updateProductStock(request.getProductStock());

        return new ProductResponse(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

        productRepository.deleteById(productId);
    }
}
