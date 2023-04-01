package com.example.backend2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RxController {

    @GetMapping("/prices")
    public Mono<Price> getProducts() throws InterruptedException {
        Thread.sleep(50);
        return Mono.just(Price.builder()
                        .price(ThreadLocalRandom.current().nextInt(201))
                .build());
    }
}
