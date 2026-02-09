package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameBranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.BranchRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UpdateBranchNameService {

    private FactoryModel factoryModel;
    private BranchRepositoryPort branchRepositoryPort;

    public UpdateBranchNameService(FactoryModel factoryModel, BranchRepositoryPort branchRepositoryPort) {
        this.factoryModel = factoryModel;
        this.branchRepositoryPort = branchRepositoryPort;
    }

    public Mono<JsonNode> executeUpdateBranchName(UpdateNameBranchRequest updateNameBranchRequest){

       BranchModel branchModel = factoryModel.buildUpdateNameDtoRequestToBranchModel(updateNameBranchRequest);

        return branchRepositoryPort.updateNameBranch(branchModel)
                .map(factoryModel::buildModelToJsonNode);
    }

}
