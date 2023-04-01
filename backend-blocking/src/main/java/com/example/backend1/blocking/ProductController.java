package com.example.backend1.blocking;

import com.example.backend1.model.Price;
import com.example.backend1.model.ProductDAO;
import com.example.backend1.model.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ProductController {

    private static RestTemplate restTemplate = new RestTemplate();
    private final ProductRepository reactiveProductRepository;

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Access-Control-Allow-Origin")
    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        final List<ProductDAO> productDAOS = new ArrayList<>();
        reactiveProductRepository.findAll().forEach(productDAOS::add);

        return productDAOS.stream()
                .map(productDAO -> ProductDTO.from(productDAO,
                        restTemplate.getForObject("http://localhost:5000/prices", Price.class).getPrice()))
                .collect(Collectors.toList());
    }
}
