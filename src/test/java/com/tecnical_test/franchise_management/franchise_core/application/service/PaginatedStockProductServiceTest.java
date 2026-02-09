package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaginatedStockProductServiceTest {

    private ProductRepositoryPort productRepositoryPort;
    private FactoryModel factoryModel;
    private PaginatedStockProductService service;

    @BeforeEach
    void setUp() {
        productRepositoryPort = mock(ProductRepositoryPort.class);
        factoryModel = mock(FactoryModel.class);
        service = new PaginatedStockProductService(productRepositoryPort, factoryModel);
    }

    @Test
    void executePaginatedStockProduct_ShouldReturnFluxOfJsonNodes() {
        int franchiseId = 1;
        ProductModel mappedModel = ProductModel.builder().franchiseId(franchiseId).build();

        ProductModel res1 = ProductModel.builder().build();
        ProductModel res2 = ProductModel.builder().build();

        JsonNode json1 = JsonNodeFactory.instance.objectNode().put("product", "A");
        JsonNode json2 = JsonNodeFactory.instance.objectNode().put("product", "B");

        when(factoryModel.buildParamDtoRequestToProductModel(franchiseId)).thenReturn(mappedModel);
        when(productRepositoryPort.paginatedStockProduct(any())).thenReturn(Flux.just(res1, res2));
        when(factoryModel.buildModelToJsonNode(res1)).thenReturn(json1);
        when(factoryModel.buildModelToJsonNode(res2)).thenReturn(json2);

        StepVerifier.create(service.executePaginatedStockProduct(franchiseId))
                .expectNext(json1)
                .expectNext(json2)
                .verifyComplete();

        verify(factoryModel).buildParamDtoRequestToProductModel(franchiseId);
        verify(productRepositoryPort).paginatedStockProduct(mappedModel);
        verify(factoryModel, times(2)).buildModelToJsonNode(any());
    }
}