package com.tecnical_test.franchise_management.franchise_core.infrastructure.entrypoints.controller;



import com.tecnical_test.franchise_management.franchise_core.application.dto.request.*;
import com.tecnical_test.franchise_management.franchise_core.application.handler.*;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

import java.util.Map;

@RestController
@RequestMapping("/Franchise_Management/Franchise_Core")
public class FranchiseController {


    private CreateFranchiseHandler createFranchiseHandler;
    private CreateBranchHandler createBranchHandler;
    private CreateProductHandler createProductHandler;
    private DeleteProductHandler deleteProductHandler;
    private UpdateStockHandler updateStockHandler;

    public FranchiseController(CreateFranchiseHandler createFranchiseHandler, CreateBranchHandler createBranchHandler,
                               CreateProductHandler createProductHandler,  DeleteProductHandler deleteProductHandler,
                               UpdateStockHandler updateStockHandler) {
        this.createFranchiseHandler = createFranchiseHandler;
        this.createBranchHandler = createBranchHandler;
        this.createProductHandler = createProductHandler;
        this.deleteProductHandler = deleteProductHandler;
        this.updateStockHandler = updateStockHandler;
    }

    @PostMapping("/CreateFranchise")
    public Mono<JsonNode> createFranchise(
            @RequestBody FranchiseRequest franchiseRequest) {
        return createFranchiseHandler.executeCreateFranchise(franchiseRequest);
    }


    @PostMapping("/CreateBranch")
    public Mono<JsonNode> createBranch(
            @RequestBody BranchRequest branchRequest) {

        return createBranchHandler.executeCreateFranchise(branchRequest);
    }

    @PostMapping("/CreateProduct")
    public Mono<JsonNode> createProduct(
            @RequestBody ProductRequest productRequest) {

        return createProductHandler.executeCreateProduct(productRequest);
    }


    @DeleteMapping("/DeleteProduct")
    public Mono<JsonNode> deleteProduct(
            @RequestBody DeleteProductRequest  deleteProductRequest) {

        return deleteProductHandler.executeDeleteProduct(deleteProductRequest);
    }


    @PutMapping("/UpdateStockProduct")
    public Mono<JsonNode> updateStockProduct(
            @RequestBody UpdateProductRequest updateProductRequest) {

        return updateStockHandler.executeUpdateProduct(updateProductRequest);
    }


    @GetMapping("/PaginatedStockProduct")
    public Flux<JsonNode> paginatedStockProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


}
