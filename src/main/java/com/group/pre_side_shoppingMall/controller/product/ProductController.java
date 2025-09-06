package com.group.pre_side_shoppingMall.controller.product;

import com.group.pre_side_shoppingMall.dto.product.request.ProductCreateRequest;
import com.group.pre_side_shoppingMall.dto.product.request.ProductGetRequest;
import com.group.pre_side_shoppingMall.dto.product.response.ProductResponse;
import com.group.pre_side_shoppingMall.service.product.ProductService;
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
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
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
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProduct(new ProductGetRequest(productId));
        return ResponseEntity.ok(response);
    }
}
