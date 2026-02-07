package com.tecnical_test.franchise_management.franchise_core.infrastructure.entrypoints.controller;



import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.handler.CreateBranchHandler;
import com.tecnical_test.franchise_management.franchise_core.application.handler.CreateFranchiseHandler;
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

    public FranchiseController(CreateFranchiseHandler createFranchiseHandler, CreateBranchHandler createBranchHandler) {
        this.createFranchiseHandler = createFranchiseHandler;
        this.createBranchHandler = createBranchHandler;
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
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


    @DeleteMapping("/DeleteProduct")
    public Mono<JsonNode> deleteProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


    @PutMapping("/UpdateStockProduct")
    public ResponseEntity<JsonNode> updateStockProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


    @GetMapping("/PaginatedStockProduct")
    public Flux<JsonNode> paginatedStockProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


}
