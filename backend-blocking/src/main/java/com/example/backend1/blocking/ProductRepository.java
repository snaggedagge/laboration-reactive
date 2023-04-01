package com.example.backend1.blocking;

import com.example.backend1.model.ProductDAO;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductDAO, String> {
}
