package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateStockServiceTest {

    private ProductRepositoryPort productRepositoryPort;
    private FactoryModel factoryModel;
    private UpdateStockService service;

    @BeforeEach
    void setUp() {
        productRepositoryPort = mock(ProductRepositoryPort.class);
        factoryModel = mock(FactoryModel.class);
        service = new UpdateStockService(productRepositoryPort, factoryModel);
    }

    @Test
    void executeUpdateStock_ShouldUpdateAndReturnJsonNode() {
        UpdateProductRequest request = new UpdateProductRequest();
        ProductModel mappedModel = ProductModel.builder().stock(50).build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("status", "stock_updated");

        when(factoryModel.buildProductUpdateDtoRequestToProductModel(request)).thenReturn(mappedModel);
        when(productRepositoryPort.updateStockProduct(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeUpdateStock(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildProductUpdateDtoRequestToProductModel(request);
        verify(productRepositoryPort).updateStockProduct(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}