package com.tecnical_test.franchise_management.franchise_core.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ResponseModel<T> {

    private Integer StatusCode;
    private String StatusMessage;
    private T data;

}
