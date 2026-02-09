package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DtoResponse;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.PaginatedStockProductService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PaginateStockProductHandlerTest {

    private PaginatedStockProductService paginatedService;
    private FactoryModel factoryModel;
    private PaginateStockProductHandler handler;

    @BeforeEach
    void setUp() {
        paginatedService = mock(PaginatedStockProductService.class);
        factoryModel = mock(FactoryModel.class);
        handler = new PaginateStockProductHandler(paginatedService, factoryModel);
    }

    @Test
    @DisplayName("Debe retornar error 206 en un Flux si el franchiseId es inválido")
    void paginatedStockProduct_ShouldReturnError_WhenIdIsInvalid() {

        int invalidId = 0;
        JsonNode mockErrorNode = JsonNodeFactory.instance.objectNode()
                .put("StatusCode", 206)
                .put("StatusMessage", "Código de franquicia obligatorio");

        when(factoryModel.dtoResponse(eq(AppConstants.CODE_206), anyString())).thenReturn(
                DtoResponse.builder()
                .StatusMessage(mockErrorNode.toString())
                .StatusCode(206)
                .build());
        when(factoryModel.buildModelToJsonNode(any())).thenReturn(mockErrorNode);

        StepVerifier.create(handler.paginatedStockProduct(invalidId))
                .expectNext(mockErrorNode)
                .verifyComplete();

        verify(paginatedService, never()).executePaginatedStockProduct(anyInt());
    }

    @Test
    @DisplayName("Debe emitir múltiples productos cuando el ID es válido")
    void paginatedStockProduct_ShouldEmitFlux_WhenIdIsValid() {

        int validId = 1;
        JsonNode product1 = JsonNodeFactory.instance.objectNode().put("name", "Producto A");
        JsonNode product2 = JsonNodeFactory.instance.objectNode().put("name", "Producto B");

        when(paginatedService.executePaginatedStockProduct(validId))
                .thenReturn(Flux.just(product1, product2));

        StepVerifier.create(handler.paginatedStockProduct(validId))
                .expectNext(product1) // Esperamos el primero
                .expectNext(product2) // Esperamos el segundo
                .verifyComplete();

        verify(paginatedService, times(1)).executePaginatedStockProduct(validId);
    }
}