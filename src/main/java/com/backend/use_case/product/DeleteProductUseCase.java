package com.backend.use_case.product;

import com.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteProductUseCase implements Function<String, Mono<String>> {
    private final ProductRepository productRepository;
    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return productRepository.deleteById(id)
                .thenReturn("removed product");
    }
}

