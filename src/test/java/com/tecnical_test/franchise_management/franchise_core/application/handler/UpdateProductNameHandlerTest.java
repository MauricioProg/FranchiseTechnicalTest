package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateProductNameService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UpdateProductNameHandlerTest {

    private UpdateProductNameService service;
    private FactoryModel factoryModel;
    private UpdateProductNameHandler handler;

    @BeforeEach
    void setUp() {
        service = mock(UpdateProductNameService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new UpdateProductNameHandler(service, factoryModel);
    }

    @Test
    void executeUpdateProductName_ShouldReturnError_WhenFranchiseIdIsNull() {
        UpdateNameProductRequest request = new UpdateNameProductRequest();
        request.setFranchiseId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProductName(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateProductName(any());
    }

    @Test
    void executeUpdateProductName_ShouldReturnError_WhenBranchIdIsNull() {
        UpdateNameProductRequest request = new UpdateNameProductRequest();
        request.setFranchiseId(1);
        request.setBranchId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProductName(request))
                .expectNext(errorNode)
                .verifyComplete();
    }

    @Test
    void executeUpdateProductName_ShouldReturnError_WhenProductIdIsNull() {
        UpdateNameProductRequest request = new UpdateNameProductRequest();
        request.setFranchiseId(1);
        request.setBranchId(2);
        request.setId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProductName(request))
                .expectNext(errorNode)
                .verifyComplete();
    }

    @Test
    void executeUpdateProductName_ShouldCallService_WhenRequestIsValid() {
        UpdateNameProductRequest request = new UpdateNameProductRequest();
        request.setFranchiseId(1);
        request.setBranchId(2);
        request.setId(3);
        request.setName("Producto Renovado");

        JsonNode successNode = JsonNodeFactory.instance.objectNode().put("status", "updated");
        when(service.executeUpdateProductName(request)).thenReturn(Mono.just(successNode));

        StepVerifier.create(handler.executeUpdateProductName(request))
                .expectNext(successNode)
                .verifyComplete();

        verify(service, times(1)).executeUpdateProductName(request);
    }
}