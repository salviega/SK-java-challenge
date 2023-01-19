package com.backend.use_case.purchase;

import com.backend.collections.Product;
import com.backend.collections.Purchase;
import com.backend.collections.Item;
import com.backend.repositories.ProductRepository;
import com.backend.repositories.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@Validated
public class BuyProductsUseCase implements Function<Purchase, Mono<String>> {
    Logger log = LoggerFactory.getLogger("QueryRouter");


    final private ProductRepository productRepository;
    final private PurchaseRepository purchaseRepository;

    public BuyProductsUseCase(ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Mono<String> apply(Purchase purchase) {
       return Flux.fromIterable(purchase.getShopping())
                .map(item ->
                    productRepository.findById(item.getId())
                            .flatMap(product -> {
                                if (!product.isEnabled()) {
                                    return Mono.just("The product" + " " + product.getName() + " " + "is not enable, please take it off the list");
                                } else {
                                    int necessaryProducts = (product.getInventory() - item.getQuantity());
                                    if (product.getInventory() - item.getQuantity() < 0) {
                                        return Mono.just("There are not sufficient" + " " + product.getName() + " " + "in the inventory, please take it off" + " " + necessaryProducts + " units");
                                    } else {
                                        return productRepository.findById(product.getId())
                                                .flatMap(foundProduct -> {
                                                    foundProduct.setId(product.getId());
                                                    foundProduct.setName(product.getName());
                                                    foundProduct.setInventory(necessaryProducts);
                                                    System.out.println(necessaryProducts);
                                                    if (necessaryProducts == 0) {
                                                        foundProduct.setEnabled(false);
                                                        foundProduct.setEnabled(product.isEnabled());
                                                        foundProduct.setMin(product.getMin());
                                                        foundProduct.setMax(foundProduct.getMax());
                                                        return productRepository.save((foundProduct));
                                                    }
                                                    foundProduct.setEnabled(product.isEnabled());
                                                    foundProduct.setMin(product.getMin());
                                                    foundProduct.setMax(foundProduct.getMax());
                                                    return productRepository.save((foundProduct));
                                                })
                                                .flatMap(product1 -> {
                                                    purchaseRepository.save(purchase);
                                                    return Mono.just("succesful");
                                                });
                                    }
                                }

                            })
                )
               .next()
               .flatMap(stringMono -> stringMono);
    }
}
