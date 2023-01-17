package com.backend.use_case.product;

import com.backend.collections.Product;
import com.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateProductUseCase implements ISaveProduct {
    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Mono<Product> apply(Product product) {
        return productRepository
                .save(product);
    }
}
