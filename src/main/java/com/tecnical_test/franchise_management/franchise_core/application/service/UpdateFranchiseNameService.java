package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameFranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.FranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UpdateFranchiseNameService {

    private FactoryModel factoryModel;
    private FranchiseRepositoryPort franchiseRepositoryPort;

    public UpdateFranchiseNameService(FactoryModel factoryModel, FranchiseRepositoryPort franchiseRepositoryPort) {
        this.factoryModel = factoryModel;
        this.franchiseRepositoryPort = franchiseRepositoryPort;
    }


    public Mono<JsonNode> executeUpdateFranchiseName(UpdateNameFranchiseRequest updateNameFranchiseRequest){

       FranchiseModel franchiseModel = factoryModel.buildUpdateNameDtoRequestToFranchiseModel(updateNameFranchiseRequest);

        return franchiseRepositoryPort.updateFranchiseName(franchiseModel)
                .map(factoryModel::buildModelToJsonNode);
    }
}
