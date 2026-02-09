package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateFranchiseService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class CreateFranchiseHandler {

    private CreateFranchiseService createFranchiseService;
    FactoryModel factoryModel;

    public CreateFranchiseHandler(CreateFranchiseService createFranchiseService, FactoryModel factoryModel) {
        this.createFranchiseService = createFranchiseService;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeCreateFranchise(FranchiseRequest franchiseRequest) {

        // Buscamos sucrusal
        if (franchiseRequest.getFranchiseName() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, "Nuevo nombre de Franquicia obligatorio"))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return createFranchiseService.executeCreateFranchise(franchiseRequest);
    }

}
