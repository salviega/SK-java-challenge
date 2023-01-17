package com.backend.use_case.product;

import com.backend.collections.Product;
import com.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetProductUseCase implements Function<String, Mono<Product>> {
    private final ProductRepository productRepository;

    public GetProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Mono<Product> apply(String id) {
        Objects.requireNonNull(id, "The Product id is require");
        return productRepository.findById(id);
    }
}
