package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateFranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateFranchiseHandlerTest {

    private CreateFranchiseService createFranchiseService;
    private FactoryModel factoryModel;
    private CreateFranchiseHandler handler;

    @BeforeEach
    void setUp() {
        // Inicializamos los mocks
        createFranchiseService = mock(CreateFranchiseService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new CreateFranchiseHandler(createFranchiseService, factoryModel);
    }

    @Test
    void executeCreateFranchise_ShouldReturnError_WhenNameIsNull() {

        FranchiseRequest request = new FranchiseRequest();
        request.setFranchiseName(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode()
                .put("StatusCode", 206)
                .put("StatusMessage", "Nuevo nombre de Franquicia obligatorio");

        when(factoryModel.dtoResponse(anyInt(), anyString())).thenReturn(DtoResponse.builder()
                .StatusMessage(mockErrorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeCreateFranchise(request))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(createFranchiseService, never()).executeCreateFranchise(any());
    }

    @Test
    void executeCreateFranchise_ShouldCallService_WhenNameIsPresent() {
        // Arrange
        FranchiseRequest request = new FranchiseRequest();
        request.setFranchiseName("Franquicia Master");

        JsonNode mockSuccessNode = JsonNodeFactory.instance.objectNode()
                .put("franchiseName", "Franquicia Master");

        when(createFranchiseService.executeCreateFranchise(request)).thenReturn(Mono.just(mockSuccessNode));

        StepVerifier.create(handler.executeCreateFranchise(request))
                .expectNext(mockSuccessNode)
                .verifyComplete();

        verify(createFranchiseService, times(1)).executeCreateFranchise(request);
    }
}