package com.tecnical_test.franchise_management.franchise_core.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateNameFranchiseRequest {

    @NotNull
    @JsonProperty("Id")
    Integer id;

    @NotNull
    @JsonProperty("Name")
    String name;

}
