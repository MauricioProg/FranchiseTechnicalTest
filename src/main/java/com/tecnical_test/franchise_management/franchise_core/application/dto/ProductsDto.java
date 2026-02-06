package com.tecnical_test.franchise_management.franchise_core.application.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigInteger;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProductsDto {

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("stock")
    private BigInteger stock;

}
