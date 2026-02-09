package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.DeleteProductService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class DeleteProductHandler {

    private DeleteProductService deleteProductService;
    private FactoryModel factoryModel;

    public DeleteProductHandler(DeleteProductService deleteProductService, FactoryModel factoryModel) {
        this.deleteProductService = deleteProductService;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeDeleteProduct(DeleteProductRequest deleteProductRequest) {

        if (deleteProductRequest.getFranchiseId() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }


        if (deleteProductRequest.getBranchId() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_BRANCH_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (deleteProductRequest.getProductId() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_PRODUCT_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return deleteProductService.executeDeleteProduct(deleteProductRequest);
    }

}
