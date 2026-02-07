package com.tecnical_test.franchise_management.franchise_core.infrastructure.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchEntity {

    private int id;

    private String name;

    private List<ProductEntity> productList;


}
