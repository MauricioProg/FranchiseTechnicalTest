package com.tecnical_test.franchise_management.franchise_core.infrastructure.entrypoints.controller;



import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.handler.CreateBranchHandler;
import com.tecnical_test.franchise_management.franchise_core.application.handler.CreateFranchiseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public Mono<JsonNode> createFranquise(
            @RequestBody FranchiseRequest franchiseRequest) {
        return createFranchiseHandler.executeCreateFranchise(franchiseRequest);
    }


    @PostMapping("/CreateBranch")
    public ResponseEntity<JsonNode> createBranch(
            @RequestBody BranchRequest branchRequest) {

        return createBranchHandler.executeCreateFranchise(branchRequest);
    }

    @PostMapping("/CreateProduct")
    public ResponseEntity<String> createProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


    @DeleteMapping("/DeleteProduct")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


    @PutMapping("/UpdateStockProduct")
    public ResponseEntity<String> updateStockProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


    @GetMapping("/UpdateStockProduct")
    public ResponseEntity<String> paginatedStockProduct(
            @RequestHeader(required = false ) Map<String, String > mapHeader) {

        return null;
    }


}
