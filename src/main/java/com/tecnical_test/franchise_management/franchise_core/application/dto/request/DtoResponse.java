package com.tecnical_test.franchise_management.franchise_core.application.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoResponse {

    private Integer StatusCode;
    private String StatusMessage;

}
