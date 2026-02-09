package com.tecnical_test.franchise_management.franchise_core.infrastructure.entrypoints.controller;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.*;
import com.tecnical_test.franchise_management.franchise_core.application.handler.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FranchiseControllerTest {

    private WebTestClient webTestClient;
    private CreateFranchiseHandler createFranchiseHandler;
    private PaginateStockProductHandler paginateStockProductHandler;
    private UpdateStockHandler updateStockHandler;
    private UpdateBranchNameHandler updateBranchNameHandler;
    private UpdateFranchiseNameHandler updateFranchiseNameHandler;
    private UpdateProductNameHandler updateProductNameHandler;
    private DeleteProductHandler deleteProductHandler;
    private CreateProductHandler createProductHandler;
    private CreateBranchHandler createBranchHandler;


    @BeforeEach
    void setUp() {

        createFranchiseHandler = mock(CreateFranchiseHandler.class);
        paginateStockProductHandler = mock(PaginateStockProductHandler.class);
        updateStockHandler = mock(UpdateStockHandler.class);
        updateProductNameHandler = mock(UpdateProductNameHandler.class);
        updateBranchNameHandler = mock(UpdateBranchNameHandler.class);
        updateFranchiseNameHandler = mock(UpdateFranchiseNameHandler.class);
        deleteProductHandler = mock(DeleteProductHandler.class);
        createProductHandler = mock(CreateProductHandler.class);
        createBranchHandler = mock(CreateBranchHandler.class);



        FranchiseController controller = new FranchiseController(
                createFranchiseHandler,
                createBranchHandler,
                createProductHandler,
                deleteProductHandler,
                updateStockHandler,
                paginateStockProductHandler,
                updateFranchiseNameHandler,
                updateBranchNameHandler,
                updateProductNameHandler
        );

        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void createFranchise_ShouldReturnStatusFromInternalJson() {
        FranchiseRequest request = new FranchiseRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 201);
        mockResponse.put("message", "Created");

        when(createFranchiseHandler.executeCreateFranchise(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/Franchise_Management/Franchise_Core/CreateFranchise")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Created");
    }

    @Test
    void updateStockProduct_ShouldRemoveStatusCodeFromJson() {
        UpdateProductRequest request = new UpdateProductRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 200);
        mockResponse.put("data", "updated");

        when(updateStockHandler.executeUpdateProduct(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.put()
                .uri("/Franchise_Management/Franchise_Core/UpdateStockProduct")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.StatusCode").doesNotExist()
                .jsonPath("$.data").isEqualTo("updated");
    }

    @Test
    void paginatedStockProduct_ShouldReturnFlux() {
        JsonNode node1 = JsonNodeFactory.instance.objectNode().put("id", 1);
        JsonNode node2 = JsonNodeFactory.instance.objectNode().put("id", 2);

        when(paginateStockProductHandler.paginatedStockProduct(1)).thenReturn(Flux.just(node1, node2));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Franchise_Management/Franchise_Core/PaginatedStockProduct")
                        .queryParam("franchiseId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(JsonNode.class)
                .hasSize(2);
    }

    @Test
    void updateNameFranchise_FullCoverage_Test() {
        // 1. Arrange
        UpdateNameFranchiseRequest request = new UpdateNameFranchiseRequest();

        // Es vital que sea un ObjectNode para que el casteo (ObjectNode) en el controller no falle
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 200);
        mockResponse.put("message", "Franchise updated");

        // Mockeamos el handler para que devuelva el Mono con el JSON
        when(updateFranchiseNameHandler.executeUpdateFranchiseName(any(UpdateNameFranchiseRequest.class)))
                .thenReturn(Mono.just(mockResponse));

        // 2. Act & Assert
        webTestClient.put()
                .uri("/Franchise_Management/Franchise_Core/UpdatNameFranchise")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk() // Esto valida que el 'code' se extrajo y usó correctamente
                .expectBody()
                .jsonPath("$.message").isEqualTo("Franchise updated")
                .jsonPath("$.StatusCode").doesNotExist(); // Esto valida que el .remove("StatusCode") se ejecutó

        // Verificamos que el handler fue llamado para asegurar que el flujo se disparó
        verify(updateFranchiseNameHandler).executeUpdateFranchiseName(any());
    }

    @Test
    void updateNameBranch_ShouldReturnStatusAndCleanJson() {
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 200);
        mockResponse.put("message", "Branch updated");

        when(updateBranchNameHandler.executeUpdateBranchName(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.put()
                .uri("/Franchise_Management/Franchise_Core/UpdateNameBranch")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.StatusCode").doesNotExist()
                .jsonPath("$.message").isEqualTo("Branch updated");
    }

    @Test
    void updateNameProduct_ShouldReturnStatusAndCleanJson() {
        UpdateNameProductRequest request = new UpdateNameProductRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 202);
        mockResponse.put("message", "Product updated");

        when(updateProductNameHandler.executeUpdateProductName(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.put()
                .uri("/Franchise_Management/Franchise_Core/UpdateNameProduct")
                .bodyValue(request)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .jsonPath("$.StatusCode").doesNotExist()
                .jsonPath("$.message").isEqualTo("Product updated");
    }

    @Test
    void createBranch_ShouldReturnStatusAndCleanJson() {
        
        BranchRequest request = new BranchRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 201);
        mockResponse.put("message", "Branch created successfully");

        when(createBranchHandler.executeCreateFranchise(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/Franchise_Management/Franchise_Core/CreateBranch")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.StatusCode").doesNotExist()
                .jsonPath("$.message").isEqualTo("Branch created successfully");
    }

    @Test
    void createProduct_ShouldReturnStatus() {
        // Arrange
        ProductRequest request = new ProductRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 201);

        // Aquí tu código no hace .remove("StatusCode"), así que el JSON lo mantiene
        when(createProductHandler.executeCreateProduct(any())).thenReturn(Mono.just(mockResponse));

        // Act & Assert
        webTestClient.post()
                .uri("/Franchise_Management/Franchise_Core/CreateProduct")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.StatusCode").isEqualTo(201);
    }

    @Test
    void deleteProduct_ShouldReturnStatusAndCleanJson() {
        // Arrange
        DeleteProductRequest request = new DeleteProductRequest();
        ObjectNode mockResponse = JsonNodeFactory.instance.objectNode();
        mockResponse.put("StatusCode", 200);
        mockResponse.put("message", "Product deleted");

        when(deleteProductHandler.executeDeleteProduct(any())).thenReturn(Mono.just(mockResponse));

        // Act & Assert
        webTestClient.method(org.springframework.http.HttpMethod.DELETE)
                .uri("/Franchise_Management/Franchise_Core/DeleteProduct")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.StatusCode").doesNotExist()
                .jsonPath("$.message").isEqualTo("Product deleted");
    }

}