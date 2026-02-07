package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.ProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateProductService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class CreateProductHandler {

    private CreateProductService createProductService;

    public CreateProductHandler(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    public Mono<JsonNode> executeCreateProduct(ProductRequest productRequest){

        if (productRequest.getFranchiseId() == null){
            return Mono.error(new IllegalArgumentException("Franchise Id es requerido"));
        }

        if (productRequest.getBranchId() == null){
            return Mono.error(new IllegalArgumentException("Branch Id es requerido"));
        }

        if (productRequest.getStock() <= 0){
            return Mono.error(new IllegalArgumentException("Stock debe ser mayor que 0"));
        }


        return createProductService.executeCreateProduct(productRequest);
    }


}
