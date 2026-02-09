package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateProductNameService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class UpdateProductNameHandler {

    private UpdateProductNameService updateProductNameService;
    private FactoryModel factoryModel;

    public UpdateProductNameHandler(UpdateProductNameService updateProductNameService, FactoryModel factoryModel) {
        this.updateProductNameService = updateProductNameService;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeUpdateProductName(UpdateNameProductRequest updateNameProductRequest){


        if (updateNameProductRequest.getFranchiseId() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }


        if (updateNameProductRequest.getBranchId() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_BRANCH_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (updateNameProductRequest.getId() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_PRODUCT_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return updateProductNameService.executeUpdateProductName(updateNameProductRequest);
    }



}
