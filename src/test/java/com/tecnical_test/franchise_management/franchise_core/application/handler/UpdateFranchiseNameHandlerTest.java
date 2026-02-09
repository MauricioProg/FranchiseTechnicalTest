package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameFranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateFranchiseNameService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UpdateFranchiseNameHandlerTest {

    private UpdateFranchiseNameService service;
    private FactoryModel factoryModel;
    private UpdateFranchiseNameHandler handler;

    @BeforeEach
    void setUp() {
        service = mock(UpdateFranchiseNameService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new UpdateFranchiseNameHandler(service, factoryModel);
    }

    @Test
    void executeUpdateFranchiseName_ShouldReturnError_WhenIdIsNull() {
        UpdateNameFranchiseRequest request = new UpdateNameFranchiseRequest();
        request.setId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(errorNode.toString())
                        .StatusCode(206)
                        .build()
        );
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateFranchiseName(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateFranchiseName(any());
    }

    @Test
    void executeUpdateFranchiseName_ShouldReturnError_WhenNameIsNull() {
        UpdateNameFranchiseRequest request = new UpdateNameFranchiseRequest();
        request.setId(1);
        request.setName(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusMessage", "Nombre requerido");
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateFranchiseName(request))
                .expectNext(errorNode)
                .verifyComplete();
    }

    @Test
    void executeUpdateFranchiseName_ShouldCallService_WhenRequestIsValid() {
        UpdateNameFranchiseRequest request = new UpdateNameFranchiseRequest();
        request.setId(1);
        request.setName("Nueva Franquicia");

        JsonNode successNode = JsonNodeFactory.instance.objectNode().put("status", "success");
        when(service.executeUpdateFranchiseName(request)).thenReturn(Mono.just(successNode));

        StepVerifier.create(handler.executeUpdateFranchiseName(request))
                .expectNext(successNode)
                .verifyComplete();

        verify(service, times(1)).executeUpdateFranchiseName(request);
    }
}