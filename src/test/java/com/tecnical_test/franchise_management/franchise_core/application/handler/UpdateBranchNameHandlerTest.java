package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameBranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateBranchNameService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UpdateBranchNameHandlerTest {

    private UpdateBranchNameService service;
    private FactoryModel factoryModel;
    private UpdateBranchNameHandler handler;

    @BeforeEach
    void setUp() {
        service = mock(UpdateBranchNameService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new UpdateBranchNameHandler(service, factoryModel);
    }

    @Test
    void executeUpdateBranchName_ShouldReturnError_WhenFranchiseIdIsInvalid() {
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        request.setFranchiseId(0);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateBranchName(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateBranchName(any());
    }

    @Test
    void executeUpdateBranchName_ShouldReturnError_WhenBranchIdIsInvalid() {
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        request.setFranchiseId(1);
        request.setId(0);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(errorNode.toString())
                        .StatusCode(206)
                        .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateBranchName(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateBranchName(any());
    }

    @Test
    void executeUpdateBranchName_ShouldReturnError_WhenNameIsNull() {
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        request.setFranchiseId(1);
        request.setId(10);
        request.setName(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusMessage", "Se requiere el nuevo nombre");
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(errorNode.toString())
                        .StatusCode(206)
                        .build()
        );
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateBranchName(request))
                .expectNext(errorNode)
                .verifyComplete();
    }

    @Test
    void executeUpdateBranchName_ShouldCallService_WhenRequestIsValid() {
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        request.setFranchiseId(1);
        request.setId(10);
        request.setName("Nuevo Nombre");

        JsonNode successNode = JsonNodeFactory.instance.objectNode().put("status", "updated");
        when(service.executeUpdateBranchName(request)).thenReturn(Mono.just(successNode));

        StepVerifier.create(handler.executeUpdateBranchName(request))
                .expectNext(successNode)
                .verifyComplete();

        verify(service, times(1)).executeUpdateBranchName(request);
    }
}