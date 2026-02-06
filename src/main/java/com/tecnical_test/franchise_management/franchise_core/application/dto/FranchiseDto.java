package com.tecnical_test.franchise_management.franchise_core.application.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FranchiseDto {

    @NotNull
    @JsonProperty("FranchiseId")
    private String id;

    @NotNull
    @JsonProperty("Name")
    private String name;

    @NotNull
    @JsonProperty("BranchId")
    private BranchDto branch;



}
