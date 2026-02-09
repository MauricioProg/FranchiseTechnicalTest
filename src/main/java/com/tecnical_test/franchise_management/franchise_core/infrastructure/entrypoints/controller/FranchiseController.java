package com.tecnical_test.franchise_management.franchise_core.infrastructure.entrypoints.controller;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.*;
import com.tecnical_test.franchise_management.franchise_core.application.handler.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/Franchise_Management/Franchise_Core")
public class FranchiseController {


    private CreateFranchiseHandler createFranchiseHandler;
    private CreateBranchHandler createBranchHandler;
    private CreateProductHandler createProductHandler;
    private DeleteProductHandler deleteProductHandler;
    private UpdateStockHandler updateStockHandler;
    private PaginateStockProductHandler paginateStockProductHandler;
    private UpdateFranchiseNameHandler updateFranchiseNameHandler;
    private UpdateBranchNameHandler updateBranchNameHandler;
    private UpdateProductNameHandler  updateProductNameHandler;

    public FranchiseController(CreateFranchiseHandler createFranchiseHandler, CreateBranchHandler createBranchHandler,
                               CreateProductHandler createProductHandler,  DeleteProductHandler deleteProductHandler,
                               UpdateStockHandler updateStockHandler, PaginateStockProductHandler paginateStockProductHandler,
                               UpdateFranchiseNameHandler updateFranchiseNameHandler, UpdateBranchNameHandler updateBranchNameHandler,
                               UpdateProductNameHandler updateProductNameHandler) {
        this.createFranchiseHandler = createFranchiseHandler;
        this.createBranchHandler = createBranchHandler;
        this.createProductHandler = createProductHandler;
        this.deleteProductHandler = deleteProductHandler;
        this.updateStockHandler = updateStockHandler;
        this.paginateStockProductHandler = paginateStockProductHandler;
        this.updateFranchiseNameHandler = updateFranchiseNameHandler;
        this.updateBranchNameHandler = updateBranchNameHandler;
        this.updateProductNameHandler = updateProductNameHandler;
    }

    @PostMapping("/CreateFranchise")
    public Mono<ResponseEntity<JsonNode>> createFranchise(
            @RequestBody FranchiseRequest franchiseRequest) {

        return createFranchiseHandler.executeCreateFranchise(franchiseRequest)
                .map(jsonNode -> {

            ObjectNode editableJsonNode = (ObjectNode) jsonNode;

            int code = editableJsonNode.get("StatusCode").asInt();

            return ResponseEntity.status(code).body(jsonNode);
        });
    }


    @PostMapping("/CreateBranch")
    public Mono<ResponseEntity<JsonNode>> createBranch(
            @RequestBody BranchRequest branchRequest) {

        return createBranchHandler.executeCreateFranchise(branchRequest)
                .map(editableJsonNode -> {

                    ObjectNode jsonNode = (ObjectNode) editableJsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    jsonNode.remove("StatusCode");

                    return ResponseEntity.status(code).body(jsonNode);
                }
        );
    }

    @PostMapping("/CreateProduct")
    public Mono<ResponseEntity<JsonNode>> createProduct(
            @RequestBody ProductRequest productRequest) {

        return createProductHandler.executeCreateProduct(productRequest)
                .map(jsonNode -> {

                    ObjectNode editableJsonNode = (ObjectNode) jsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    return ResponseEntity.status(code).body(jsonNode);
                });
    }


    @DeleteMapping("/DeleteProduct")
    public Mono<ResponseEntity<JsonNode>> deleteProduct(
            @RequestBody DeleteProductRequest  deleteProductRequest) {

        return deleteProductHandler.executeDeleteProduct(deleteProductRequest)
                .map(editableJsonNode -> {

                    ObjectNode jsonNode = (ObjectNode) editableJsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    jsonNode.remove("StatusCode");

                    return ResponseEntity.status(code).body(jsonNode);
                }
        );
    }


    @PutMapping("/UpdateStockProduct")
    public Mono<ResponseEntity<JsonNode>> updateStockProduct(
            @RequestBody UpdateProductRequest updateProductRequest) {

        return updateStockHandler.executeUpdateProduct(updateProductRequest)
                .map(editableJsonNode -> {

                    ObjectNode jsonNode = (ObjectNode) editableJsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    jsonNode.remove("StatusCode");

                    return ResponseEntity.status(code).body(jsonNode);
                }
        );
    }


    @GetMapping("/PaginatedStockProduct")
    public Flux<JsonNode> paginatedStockProduct(
            @RequestParam int franchiseId) {

        return paginateStockProductHandler.paginatedStockProduct(franchiseId);
    }

    @PutMapping("/UpdatNameFranchise")
    public Mono<ResponseEntity<JsonNode>> updateNameFranchise(
            @RequestBody UpdateNameFranchiseRequest updateNameFranchiseRequest) {

        return updateFranchiseNameHandler.executeUpdateFranchiseName(updateNameFranchiseRequest)
                .map(editableJsonNode -> {

                    ObjectNode jsonNode = (ObjectNode) editableJsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    jsonNode.remove("StatusCode");

                    return ResponseEntity.status(code).body(jsonNode);
                }
        );
    }

    @PutMapping("/UpdateNameBranch")
    public Mono<ResponseEntity<JsonNode>> updateNameBranch(
            @RequestBody UpdateNameBranchRequest updateNameBranchRequest) {

        return updateBranchNameHandler.executeUpdateBranchName(updateNameBranchRequest)
                .map(editableJsonNode -> {

                    ObjectNode jsonNode = (ObjectNode) editableJsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    jsonNode.remove("StatusCode");

                    return ResponseEntity.status(code).body(jsonNode);
                }
        );
    }

    @PutMapping("/UpdateNameProduct")
    public Mono<ResponseEntity<JsonNode>> updateNameProduct(
            @RequestBody UpdateNameProductRequest updateNameProductRequest) {

        return updateProductNameHandler.executeUpdateProductName(updateNameProductRequest)
                .map(editableJsonNode -> {

                    ObjectNode jsonNode = (ObjectNode) editableJsonNode;

                    int code = editableJsonNode.get("StatusCode").asInt();

                    jsonNode.remove("StatusCode");

                    return ResponseEntity.status(code).body(jsonNode);
                }
        );
    }


}
