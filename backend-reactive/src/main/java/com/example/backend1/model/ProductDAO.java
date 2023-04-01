package com.example.backend1.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "product")
@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductDAO {
    @Id
    private String id;
    private String name;
}
