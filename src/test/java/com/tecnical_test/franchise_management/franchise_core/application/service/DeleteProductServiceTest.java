package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.Mockito.*;

class DeleteProductServiceTest {

    private FactoryModel factoryModel;
    private ProductRepositoryPort productRepositoryPort;
    private DeleteProductService service;

    @BeforeEach
    void setUp() {
        factoryModel = mock(FactoryModel.class);
        productRepositoryPort = mock(ProductRepositoryPort.class);
        service = new DeleteProductService(factoryModel, productRepositoryPort);
    }

    @Test
    void executeDeleteProduct_ShouldDeleteAndReturnJsonNode() {
        DeleteProductRequest request = new DeleteProductRequest();
        ProductModel mappedModel = ProductModel.builder().idProduct(5).build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("message", "deleted");

        when(factoryModel.buildProductDeleteDtoRequestToProductModel(request)).thenReturn(mappedModel);
        when(productRepositoryPort.deleteProduct(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeDeleteProduct(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildProductDeleteDtoRequestToProductModel(request);
        verify(productRepositoryPort).deleteProduct(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}