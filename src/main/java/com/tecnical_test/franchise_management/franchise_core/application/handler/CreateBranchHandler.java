package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateBranchService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class CreateBranchHandler {

    private CreateBranchService createBranchService;
    private FactoryModel  factoryModel;

    public CreateBranchHandler(CreateBranchService createBranchService) {
        this.createBranchService = createBranchService;
    }

    public Mono<JsonNode> executeCreateFranchise(BranchRequest branchRequest) {

        if (branchRequest.getFranchiseId() <= 0) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);

        }

        if (branchRequest.getBranchName() == null) {
            return Mono.just(factoryModel.dtoResponse(AppConstants.CODE_206, "Nuevo Nombre de Sucursal Obligatorio"))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return createBranchService.executeCreateBranch(branchRequest);
    }

}
