package com.example.backend1.reactive;

import com.example.backend1.model.Price;
import com.example.backend1.model.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@AllArgsConstructor
public class ProductsController {

    private static WebClient client = WebClient.create("http://localhost:5000");
    private final ReactiveProductRepository reactiveProductRepository;

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Access-Control-Allow-Origin")
    @GetMapping("/products")
    public Flux<ProductDTO> getProducts() {
        return reactiveProductRepository.findAll()
                .publishOn(Schedulers.boundedElastic())
                .map(productDAO -> {
                    ResponseEntity<Price> price = client.get().uri("/prices")
                            .retrieve()
                            .toEntity(Price.class)
                            .block();
                    price.getHeaders().forEach((key, value) -> {
                        System.out.printf("Response Header " + key + ": " + value + "\n");
                    });
                    return ProductDTO.from(productDAO, price.getBody().getPrice());
        });
    }
}
