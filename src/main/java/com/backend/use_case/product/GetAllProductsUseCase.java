package com.backend.use_case.product;

import com.backend.collections.Product;
import com.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllProductsUseCase implements Supplier<Flux<Product>> {

    private final ProductRepository productRepository;

    public GetAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> get() {
        return productRepository.findAll();
    }
}
