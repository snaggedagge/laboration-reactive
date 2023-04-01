package com.example.backend1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "product_dao")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductDAO {
    @Id
    private String id;
    private String name;
}
