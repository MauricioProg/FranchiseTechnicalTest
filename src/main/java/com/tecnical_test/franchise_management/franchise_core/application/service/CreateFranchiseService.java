package com.tecnical_test.franchise_management.franchise_core.application.service;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.SaveFranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;


@Service
public class CreateFranchiseService {

    private SaveFranchiseRepositoryPort saveFranchiseRepositoryPort;
    private FactoryModel factoryModel;

    public CreateFranchiseService(SaveFranchiseRepositoryPort saveFranchiseRepositoryPort, FactoryModel factoryModel) {
        this.saveFranchiseRepositoryPort = saveFranchiseRepositoryPort;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeCreateFranchise(FranchiseRequest franchiseRequest) {


        FranchiseModel franchiseModel = factoryModel.buildDtoRequestToModel(franchiseRequest);

        saveFranchiseRepositoryPort.saveFranchise(franchiseModel);


        return new Mono<JsonNode>() {
            @Override
            public void subscribe(CoreSubscriber<? super JsonNode> coreSubscriber) {

            }
        };
    }



}
