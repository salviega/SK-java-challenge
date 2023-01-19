package com.backend.use_case.purchase;

import com.backend.collections.Purchase;
import com.backend.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllPurchasesUseCase implements Supplier<Flux<Purchase>> {
    private final PurchaseRepository purchaseRepository;

    public GetAllPurchasesUseCase(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Flux<Purchase> get() {
        return purchaseRepository.findAll();
    }
}
