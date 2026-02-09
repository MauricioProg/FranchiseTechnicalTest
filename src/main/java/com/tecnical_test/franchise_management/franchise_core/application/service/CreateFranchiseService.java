package com.tecnical_test.franchise_management.franchise_core.application.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.FranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class CreateFranchiseService {

    private FranchiseRepositoryPort franchiseRepositoryPort;
    private FactoryModel factoryModel;

    public CreateFranchiseService(FranchiseRepositoryPort franchiseRepositoryPort, FactoryModel factoryModel) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeCreateFranchise(FranchiseRequest franchiseRequest) {


        FranchiseModel franchiseModel = factoryModel.buildFranchiseDtoRequestToFranchiseModel(franchiseRequest);

        return franchiseRepositoryPort.saveFranchise(franchiseModel)
                .map(savedModel -> factoryModel.buildModelToJsonNode(savedModel));
    }






}
