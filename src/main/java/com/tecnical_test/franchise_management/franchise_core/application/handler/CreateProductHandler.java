package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.ProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateProductService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class CreateProductHandler {

    private CreateProductService createProductService;
    private FactoryModel factoryModel;

    public CreateProductHandler(CreateProductService createProductService, FactoryModel factoryModel) {
        this.createProductService = createProductService;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeCreateProduct(ProductRequest productRequest){

        if (productRequest.getFranchiseId() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (productRequest.getBranchId() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_BRANCH_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (productRequest.getStock() <= 0){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, "Stock debe ser mayor que 0"))
                    .map(factoryModel::buildModelToJsonNode);
        }


        return createProductService.executeCreateProduct(productRequest);
    }


}
