package com.tecnical_test.franchise_management.franchise_core.infrastructure.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    private int id;

    private String name;

    private String stock;

}
