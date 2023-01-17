package com.backend.use_case.product;

import com.backend.collections.Product;
import com.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Objects;

@Service
@Validated
public class UpdateProductUseCase implements  IUpdateProduct{
    private final ProductRepository productRepository;
    private final GetProductUseCase getProductUseCase;

    public UpdateProductUseCase(ProductRepository productRepository, GetProductUseCase getProductUseCase) {
        this.productRepository = productRepository;
        this.getProductUseCase = getProductUseCase;
    }

    @Override
    public Mono<Product> apply(String id, Product product) {
        Objects.requireNonNull(id, "Id of the Product is required");
        return getProductUseCase.apply(id)
                .flatMap(foundProduct -> {
                    foundProduct.setId(product.getId());
                    foundProduct.setName(product.getName());
                    foundProduct.setInventory(product.getInventory());
                    foundProduct.setEnabled(product.isEnabled());
                    foundProduct.setMin(product.getMin());
                    foundProduct.setMax(foundProduct.getMax());
                    return productRepository
                            .save(foundProduct);
                });
    }
}


