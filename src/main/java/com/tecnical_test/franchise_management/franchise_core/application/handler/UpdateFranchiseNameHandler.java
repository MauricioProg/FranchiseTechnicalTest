package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameFranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateFranchiseNameService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class UpdateFranchiseNameHandler {

    private UpdateFranchiseNameService updateFranchiseNameService;
    private FactoryModel factoryModel;

    public UpdateFranchiseNameHandler(UpdateFranchiseNameService updateFranchiseNameService) {
        this.updateFranchiseNameService = updateFranchiseNameService;
    }

    public Mono<JsonNode> executeUpdateFranchiseName(UpdateNameFranchiseRequest updateNameFranchiseRequest){

        if (updateNameFranchiseRequest.getId() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        if (updateNameFranchiseRequest.getName() == null){
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, "Se requiere el nuevo nombre para la franquicia"))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return updateFranchiseNameService.executeUpdateFranchiseName(updateNameFranchiseRequest);
    }

}
