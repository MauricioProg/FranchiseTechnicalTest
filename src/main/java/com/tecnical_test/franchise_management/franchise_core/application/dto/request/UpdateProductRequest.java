package com.tecnical_test.franchise_management.franchise_core.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateProductRequest {


    @NotNull
    @JsonProperty("ProductId")
    private Integer productId;

    @NotNull
    @JsonProperty("FranchiseId")
    private String FranchiseId;

    @NotNull
    @JsonProperty("BranchId")
    private String BranchId;

    @NotNull
    @JsonProperty("Stock")
    private Integer stock;

}
