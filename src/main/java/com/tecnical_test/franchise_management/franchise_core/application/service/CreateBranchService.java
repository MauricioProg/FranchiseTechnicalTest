package com.tecnical_test.franchise_management.franchise_core.application.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.BranchRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateBranchService {

    private FactoryModel factoryModel;
    private BranchRepositoryPort branchRepositoryPort;

    public CreateBranchService(FactoryModel factoryModel, BranchRepositoryPort branchRepositoryPort) {
        this.factoryModel = factoryModel;
        this.branchRepositoryPort = branchRepositoryPort;
    }

    public Mono<JsonNode> executeCreateBranch(BranchRequest branchRequest) {


        BranchModel branchModel = factoryModel.buildBranchDtoRequestToBranchModel(branchRequest);

        return branchRepositoryPort.saveBranch(branchModel)
                .map(savedModel -> factoryModel.buildModelToJsonNode(savedModel));

    }

}
