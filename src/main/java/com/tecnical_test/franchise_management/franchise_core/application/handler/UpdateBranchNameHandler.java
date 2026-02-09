package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameBranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateBranchNameService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;



@Component
public class UpdateBranchNameHandler {

    private UpdateBranchNameService updateBranchNameService;
    private FactoryModel factoryModel;

    public UpdateBranchNameHandler(UpdateBranchNameService updateBranchNameService, FactoryModel factoryModel) {
        this.updateBranchNameService = updateBranchNameService;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeUpdateBranchName(UpdateNameBranchRequest updateNameBranchRequest) {

        if (updateNameBranchRequest.getFranchiseId() <= 0) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (updateNameBranchRequest.getId() <= 0) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_BRANCH_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (updateNameBranchRequest.getName() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, "Se requiere el nuevo nombre para la sucursal"))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return updateBranchNameService.executeUpdateBranchName(updateNameBranchRequest);
    }

}
