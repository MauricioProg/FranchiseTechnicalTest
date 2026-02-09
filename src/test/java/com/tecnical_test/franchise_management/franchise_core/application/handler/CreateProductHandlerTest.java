package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.ProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateProductService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CreateProductHandlerTest {

    private CreateProductService createProductService;
    private FactoryModel factoryModel;
    private CreateProductHandler handler;

    @BeforeEach
    void setUp() {
        createProductService = mock(CreateProductService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new CreateProductHandler(createProductService, factoryModel);
    }

    @Test
    void executeCreateProduct_ShouldReturnError_WhenFranchiseIdIsNull() {

        ProductRequest request = new ProductRequest();
        request.setFranchiseId(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(mockErrorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeCreateProduct(request))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(createProductService, never()).executeCreateProduct(any());
    }

    @Test
    void executeCreateProduct_ShouldReturnError_WhenBranchIdIsNull() {

        ProductRequest request = new ProductRequest();
        request.setFranchiseId(1);
        request.setBranchId(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(mockErrorNode.toString())
                        .StatusCode(206)
                        .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeCreateProduct(request))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(createProductService, never()).executeCreateProduct(any());
    }


    @Test
    void executeCreateProduct_ShouldReturnError_WhenStockIsZeroOrLess() {

        ProductRequest request = new ProductRequest();
        request.setFranchiseId(1);
        request.setBranchId(10);
        request.setStock(0);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusMessage", "Stock debe ser mayor que 0");
        when(factoryModel.dtoResponse(anyInt(), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(mockErrorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeCreateProduct(request))
                .expectNext(mockErrorNode)
                .verifyComplete();
    }



    @Test
    void executeCreateProduct_ShouldCallService_WhenRequestIsCorrect() {

        ProductRequest request = new ProductRequest();
        request.setFranchiseId(1);
        request.setBranchId(10);
        request.setStock(50);
        request.setProductName("Producto Test");

        JsonNode mockSuccessNode = JsonNodeFactory.instance.objectNode().put("productName", "Producto Test");
        when(createProductService.executeCreateProduct(request)).thenReturn(Mono.just(mockSuccessNode));

        StepVerifier.create(handler.executeCreateProduct(request))
                .expectNext(mockSuccessNode)
                .verifyComplete();

        verify(createProductService, times(1)).executeCreateProduct(request);
    }
}