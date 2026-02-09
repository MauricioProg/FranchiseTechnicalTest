package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateProductNameServiceTest {

    private FactoryModel factoryModel;
    private ProductRepositoryPort productRepositoryPort;
    private UpdateProductNameService service;

    @BeforeEach
    void setUp() {
        factoryModel = mock(FactoryModel.class);
        productRepositoryPort = mock(ProductRepositoryPort.class);
        service = new UpdateProductNameService(factoryModel, productRepositoryPort);
    }

    @Test
    void executeUpdateProductName_ShouldUpdateAndReturnJsonNode() {
        UpdateNameProductRequest request = new UpdateNameProductRequest();
        ProductModel mappedModel = ProductModel.builder().productName("Producto Actualizado").build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("status", "success");

        when(factoryModel.buildUpdateNameDtoRequestToProductModel(request)).thenReturn(mappedModel);
        when(productRepositoryPort.updateNameProduct(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeUpdateProductName(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildUpdateNameDtoRequestToProductModel(request);
        verify(productRepositoryPort).updateNameProduct(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}