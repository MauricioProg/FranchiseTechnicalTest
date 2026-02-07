package com.tecnical_test.franchise_management.franchise_core.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductModel {

    private int idProduct;
    private int franchiseId;
    private int branchId;
    private String productName;
    private int stock;


}
