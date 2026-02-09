package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.FranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.Mockito.*;

class CreateFranchiseServiceTest {

    private FranchiseRepositoryPort franchiseRepositoryPort;
    private FactoryModel factoryModel;
    private CreateFranchiseService service;

    @BeforeEach
    void setUp() {
        franchiseRepositoryPort = mock(FranchiseRepositoryPort.class);
        factoryModel = mock(FactoryModel.class);
        service = new CreateFranchiseService(franchiseRepositoryPort, factoryModel);
    }

    @Test
    void executeCreateFranchise_ShouldSaveAndReturnJsonNode() {
        FranchiseRequest request = new FranchiseRequest();
        FranchiseModel mappedModel = FranchiseModel.builder().franchiseName("Franquicia Test").build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("status", "created");

        when(factoryModel.buildFranchiseDtoRequestToFranchiseModel(request)).thenReturn(mappedModel);
        when(franchiseRepositoryPort.saveFranchise(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeCreateFranchise(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildFranchiseDtoRequestToFranchiseModel(request);
        verify(franchiseRepositoryPort).saveFranchise(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}