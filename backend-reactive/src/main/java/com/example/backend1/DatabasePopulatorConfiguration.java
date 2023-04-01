package com.example.backend1;

import com.example.backend1.model.ProductDAO;
import com.example.backend1.reactive.ReactiveProductRepository;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn({"initializer"})
public class DatabasePopulatorConfiguration {

    public DatabasePopulatorConfiguration(final ReactiveProductRepository repository) {
        if (!repository.findAll().toIterable().iterator().hasNext()) {
            for (int i=0;i<30;i++) {
                repository.save(ProductDAO.builder()
                        .name("Biography of " + new Faker().name().fullName())
                        .build()).block();
            }
        }
    }
}
