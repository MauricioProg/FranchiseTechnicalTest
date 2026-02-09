package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateBranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateBranchHandlerTest {

    private CreateBranchService createBranchService;
    private FactoryModel factoryModel;
    private CreateBranchHandler handler;

    @BeforeEach
    void setUp() {
        // Creamos los mocks
        createBranchService = mock(CreateBranchService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new CreateBranchHandler(factoryModel, createBranchService);
    }

    @Test
    void executeCreateFranchise_ShouldReturnError_WhenFranchiseIdIsInvalid() {

        BranchRequest request = new BranchRequest();
        request.setFranchiseId(0);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);

        when(factoryModel.dtoResponse(anyInt(), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(mockErrorNode.toString())
                        .StatusCode(206)
                        .build()
        );
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        // Act & Assert
        StepVerifier.create(handler.executeCreateFranchise(request))
                .expectNextMatches(node -> node.get("StatusCode").asInt() == 206)
                .verifyComplete();

        verify(createBranchService, never()).executeCreateBranch(any());
    }

    @Test
    void executeCreateFranchise_ShouldReturnError_WhenBranchNameIsInvalid() {

        BranchRequest request = new BranchRequest();
        request.setFranchiseId(1);
        request.setBranchName(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);

        when(factoryModel.dtoResponse(anyInt(), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(mockErrorNode.toString())
                        .StatusCode(206)
                        .build()
        );
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeCreateFranchise(request))
                .expectNextMatches(node -> node.get("StatusCode").asInt() == 206)
                .verifyComplete();

        verify(createBranchService, never()).executeCreateBranch(any());
    }

    @Test
    void executeCreateFranchise_ShouldCallService_WhenRequestIsValid() {

        BranchRequest request = new BranchRequest();
        request.setFranchiseId(1);
        request.setBranchName("Sucursal Norte");

        JsonNode mockSuccessNode = JsonNodeFactory.instance.objectNode().put("status", "ok");
        when(createBranchService.executeCreateBranch(request)).thenReturn(Mono.just(mockSuccessNode));

        StepVerifier.create(handler.executeCreateFranchise(request))
                .expectNext(mockSuccessNode)
                .verifyComplete();

        verify(createBranchService, times(1)).executeCreateBranch(request);
    }
}