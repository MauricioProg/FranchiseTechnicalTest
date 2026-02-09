package com.tecnical_test.franchise_management.franchise_core.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel<T> {

    @JsonProperty("StatusCode")
    private Integer StatusCode;
    private String StatusMessage;
    private T data;

}
