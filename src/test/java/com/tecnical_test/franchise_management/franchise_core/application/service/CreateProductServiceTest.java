package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.ProductRequest;
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

import static org.mockito.Mockito.*;

class CreateProductServiceTest {

    private FactoryModel factoryModel;
    private ProductRepositoryPort productRepositoryPort;
    private CreateProductService service;

    @BeforeEach
    void setUp() {
        factoryModel = mock(FactoryModel.class);
        productRepositoryPort = mock(ProductRepositoryPort.class);
        service = new CreateProductService(factoryModel, productRepositoryPort);
    }

    @Test
    void executeCreateProduct_ShouldCreateAndReturnJsonNode() {
        ProductRequest request = new ProductRequest();
        ProductModel mappedModel = ProductModel.builder().productName("Producto Test").build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("product", "saved");

        when(factoryModel.buildProductDtoRequestToProductModel(request)).thenReturn(mappedModel);
        when(productRepositoryPort.createProduct(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeCreateProduct(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildProductDtoRequestToProductModel(request);
        verify(productRepositoryPort).createProduct(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}