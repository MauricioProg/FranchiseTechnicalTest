package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameFranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.FranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateFranchiseNameServiceTest {

    private FactoryModel factoryModel;
    private FranchiseRepositoryPort franchiseRepositoryPort;
    private UpdateFranchiseNameService service;

    @BeforeEach
    void setUp() {
        factoryModel = mock(FactoryModel.class);
        franchiseRepositoryPort = mock(FranchiseRepositoryPort.class);
        service = new UpdateFranchiseNameService(factoryModel, franchiseRepositoryPort);
    }

    @Test
    void executeUpdateFranchiseName_ShouldUpdateAndReturnJsonNode() {
        UpdateNameFranchiseRequest request = new UpdateNameFranchiseRequest();
        FranchiseModel mappedModel = FranchiseModel.builder().franchiseName("Nueva Franquicia").build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("status", "success");

        when(factoryModel.buildUpdateNameDtoRequestToFranchiseModel(request)).thenReturn(mappedModel);
        when(franchiseRepositoryPort.updateFranchiseName(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeUpdateFranchiseName(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildUpdateNameDtoRequestToFranchiseModel(request);
        verify(franchiseRepositoryPort).updateFranchiseName(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}