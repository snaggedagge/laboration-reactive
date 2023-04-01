package com.example.backend1.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private String name;
    private double price;

    public static ProductDTO from(final ProductDAO dao, final double price) {
        return ProductDTO.builder()
                .name(dao.getName())
                .price(price)
                .build();
    }
}
