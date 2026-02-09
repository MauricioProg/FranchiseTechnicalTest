package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateStockService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class UpdateStockHandler {

    private UpdateStockService updateStockService;
    private FactoryModel factoryModel;

    public UpdateStockHandler(UpdateStockService updateStockService, FactoryModel factoryModel) {
        this.updateStockService = updateStockService;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeUpdateProduct(UpdateProductRequest updateProductRequest){

        if (updateProductRequest.getBranchId() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_BRANCH_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (updateProductRequest.getProductId() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_PRODUCT_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (updateProductRequest.getFranchiseId() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (0 >=  updateProductRequest.getStock()) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, "Stock debe ser mayor mayor 0"))
                    .map(factoryModel::buildModelToJsonNode);
        }


        return updateStockService.executeUpdateStock(updateProductRequest);
    };

}
