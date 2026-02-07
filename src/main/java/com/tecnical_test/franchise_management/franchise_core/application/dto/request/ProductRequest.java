package com.tecnical_test.franchise_management.franchise_core.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProductRequest {

    @NotNull
    @JsonProperty("ProductName")
    private String productName;

    @NotNull
    @JsonProperty("FranchiseId")
    private Integer franchiseId;

    @NotNull
    @JsonProperty("BranchId")
    private Integer branchId;

    @NotNull
    @JsonProperty("Stock")
    private Integer stock;
}
