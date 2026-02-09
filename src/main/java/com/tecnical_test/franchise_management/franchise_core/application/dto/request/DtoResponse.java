package com.tecnical_test.franchise_management.franchise_core.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoResponse {

    @JsonProperty("StatusCode")
    private Integer StatusCode;
    private String StatusMessage;

}
