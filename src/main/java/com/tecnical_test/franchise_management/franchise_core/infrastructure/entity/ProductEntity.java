package com.tecnical_test.franchise_management.franchise_core.infrastructure.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "products")
public class ProductEntity {

    @Id
    private int numIdProduct;

    private int branchId;

    private String name;

    private String stock;

}
