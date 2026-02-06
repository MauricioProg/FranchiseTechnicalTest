package com.tecnical_test.franchise_management.franchise_core.application.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BranchDto {

    @JsonProperty
    @NotNull
    private String branchId;

    @JsonProperty
    private String branchName;

    @JsonProperty
    private ProductsDto products;


}
