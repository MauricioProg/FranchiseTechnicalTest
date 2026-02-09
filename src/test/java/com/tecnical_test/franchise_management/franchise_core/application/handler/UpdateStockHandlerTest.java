package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateStockService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UpdateStockHandlerTest {

    private UpdateStockService service;
    private FactoryModel factoryModel;
    private UpdateStockHandler handler;

    @BeforeEach
    void setUp() {
        service = mock(UpdateStockService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new UpdateStockHandler(service, factoryModel);
    }

    @Test
    void executeUpdateProduct_ShouldReturnError_WhenBranchIdIsNull() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setBranchId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProduct(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateStock(any());
    }


    @Test
    void executeUpdateProduct_ShouldReturnError_WhenProductIdIsNull() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setBranchId("1");
        request.setProductId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(errorNode.toString())
                        .StatusCode(206)
                        .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProduct(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateStock(any());
    }

    @Test
    void executeUpdateProduct_ShouldReturnError_WhenFranchiseIdIsNull() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setBranchId("1");
        request.setProductId(10);
        request.setFranchiseId(null);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(errorNode.toString())
                        .StatusCode(206)
                        .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProduct(request))
                .expectNext(errorNode)
                .verifyComplete();

        verify(service, never()).executeUpdateStock(any());
    }



    @Test
    void executeUpdateProduct_ShouldReturnError_WhenStockIsInvalid() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setBranchId("1");
        request.setProductId(10);
        request.setFranchiseId("100");
        request.setStock(0);

        JsonNode errorNode = JsonNodeFactory.instance.objectNode().put("StatusMessage", "Stock debe ser mayor mayor 0");
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(errorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(errorNode);

        StepVerifier.create(handler.executeUpdateProduct(request))
                .expectNext(errorNode)
                .verifyComplete();
    }

    @Test
    void executeUpdateProduct_ShouldCallService_WhenRequestIsValid() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setBranchId("1");
        request.setProductId(10);
        request.setFranchiseId("100");
        request.setStock(50);

        JsonNode successNode = JsonNodeFactory.instance.objectNode().put("status", "stock_updated");
        when(service.executeUpdateStock(request)).thenReturn(Mono.just(successNode));

        StepVerifier.create(handler.executeUpdateProduct(request))
                .expectNext(successNode)
                .verifyComplete();

        verify(service, times(1)).executeUpdateStock(request);
    }
}