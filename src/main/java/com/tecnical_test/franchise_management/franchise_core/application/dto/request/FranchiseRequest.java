package com.tecnical_test.franchise_management.franchise_core.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FranchiseRequest {

    @NotNull
    @JsonProperty("FranchiseName")
    private String franchiseName;

}
