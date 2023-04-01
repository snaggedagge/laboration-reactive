package com.example.backend1.reactive;

import com.example.backend1.model.ProductDAO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveProductRepository extends ReactiveCrudRepository<ProductDAO, String> {
}
