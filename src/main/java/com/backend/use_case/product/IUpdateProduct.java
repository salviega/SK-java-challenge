package com.backend.use_case.product;

import com.backend.collections.Product;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface IUpdateProduct {
    public Mono<Product> apply(@Valid String id, Product product);
}
