package com.backend.repositories;

import com.backend.collections.Purchase;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PurchaseRepository extends ReactiveCrudRepository<Purchase, String> {
}
