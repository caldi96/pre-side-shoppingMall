package com.group.pre_side_shoppingMall.controller.product;

import com.group.pre_side_shoppingMall.dto.product.request.ProductCreateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductGetRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductPriceUpdateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductStockUpdateRequest;
import com.group.pre_side_shoppingMall.dto.product.response.ProductResponse;
import com.group.pre_side_shoppingMall.service.product.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품등록
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    // 상품 전체 리스트 가져오기(다건)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> responses = productService.getProducts();
        return ResponseEntity.ok(responses);
    }

    // 상품정보 가져오기(단건)
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable
            @Min(value = 1, message = "상품 ID는 1 이상의 값이어야 합니다.")
            Long productId) {
        ProductResponse response = productService.getProduct(new ProductGetRequest(productId));
        return ResponseEntity.ok(response);
    }

    // 상품 가격 수정
    @PutMapping("/price")
    public ResponseEntity<ProductResponse> updateProductPrice(@RequestBody @Valid ProductPriceUpdateRequest request) {
        ProductResponse response = productService.updateProductPrice(request);
        return ResponseEntity.ok(response);
    }

    // 상품 수량 수정
    @PutMapping("/stock")
    public ResponseEntity<ProductResponse> updateProductStock(@RequestBody @Valid ProductStockUpdateRequest request) {
        ProductResponse response = productService.updateProductStock(request);
        return ResponseEntity.ok(response);
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable
            @Min(value = 1, message = "상품 ID는 1 이상의 값이어야 합니다.")
            Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("상품이 삭제되었습니다");
    }
}
