package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.DeleteProductService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DeleteProductHandlerTest {

    private DeleteProductService deleteProductService;
    private FactoryModel factoryModel;
    private DeleteProductHandler handler;

    @BeforeEach
    void setUp() {
        deleteProductService = mock(DeleteProductService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new DeleteProductHandler(deleteProductService, factoryModel);
    }

    @Test
    void executeDeleteProduct_ShouldReturnError_WhenFranchiseIdIsNull() {
        // Arrange
        DeleteProductRequest request = new DeleteProductRequest();
        request.setFranchiseId(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(mockErrorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeDeleteProduct(request))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(deleteProductService, never()).executeDeleteProduct(any());
    }


    @Test
    void executeDeleteProduct_ShouldReturnError_WhenBranchIdIsNull() {

        DeleteProductRequest request = new DeleteProductRequest();
        request.setFranchiseId("1");
        request.setBranchId(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                        .StatusMessage(mockErrorNode.toString())
                        .StatusCode(206)
                        .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeDeleteProduct(request))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(deleteProductService, never()).executeDeleteProduct(any());
    }

    @Test
    void executeDeleteProduct_ShouldReturnError_WhenProductIdIsNull() {

        DeleteProductRequest request = new DeleteProductRequest();
        request.setFranchiseId("1");
        request.setBranchId("10");
        request.setProductId(null);

        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode().put("StatusCode", 206);
        when(factoryModel.dtoResponse(anyInt(), anyString())).thenReturn(DtoResponse.builder()
                .StatusMessage(mockErrorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.executeDeleteProduct(request))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(deleteProductService, never()).executeDeleteProduct(any());
    }

    @Test
    void executeDeleteProduct_ShouldCallService_WhenRequestIsCorrect() {

        DeleteProductRequest request = new DeleteProductRequest();
        request.setFranchiseId("1");
        request.setBranchId("10");
        request.setProductId(5);

        JsonNode mockSuccessNode = JsonNodeFactory.instance.objectNode().put("StatusMessage", "Producto eliminado");
        when(deleteProductService.executeDeleteProduct(request)).thenReturn(Mono.just(mockSuccessNode));

        StepVerifier.create(handler.executeDeleteProduct(request))
                .expectNext(mockSuccessNode)
                .verifyComplete();

        verify(deleteProductService, times(1)).executeDeleteProduct(request);
    }
}