package com.tdhbuy.product_service.service;

import com.tdhbuy.product_service.dto.ProductRequest;
import com.tdhbuy.product_service.dto.ProductResponse;
import com.tdhbuy.product_service.mapper.ProductMapper;
import com.tdhbuy.product_service.model.Product;
import com.tdhbuy.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        productRepository.save(product);
        log.info("Creating product: {}", product.getId());
    }

    public List<ProductResponse> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toProductResponse).toList();
    }
}
