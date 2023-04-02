package com.example.backend1.reactive;

import com.example.backend1.model.Price;
import com.example.backend1.model.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@AllArgsConstructor
public class ProductsController {

    private static WebClient client = WebClient.create("http://localhost:5000");
    private final ReactiveProductRepository reactiveProductRepository;

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Access-Control-Allow-Origin")
    @GetMapping("/products")
    public Flux<ProductDTO> getProducts(@RequestParam(required = false) final boolean triggerFailure) {
        AtomicInteger counter = new AtomicInteger(0);
        return reactiveProductRepository.findAll()
                .publishOn(Schedulers.boundedElastic())
                .map(productDAO -> {
                    String uri = "/prices";
                    if (triggerFailure && counter.incrementAndGet() > 20) {
                        uri = "/prices?triggerFailure=true";
                    }
                    ResponseEntity<Price> price = client.get().uri(uri)
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
