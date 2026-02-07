package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class CreateBranchHandler {

    private CreateBranchService createBranchService;

    public CreateBranchHandler(CreateBranchService createBranchService) {
        this.createBranchService = createBranchService;
    }

    public Mono<JsonNode> executeCreateFranchise(BranchRequest branchRequest) {

        if (branchRequest.getFranchiseId() == null) {
            return Mono.error(new IllegalArgumentException("Franquicia id es requerido"));

        }

        if (branchRequest.getBranchName() == null) {
            return Mono.error(new IllegalArgumentException("El nombre de la sucursal es requerido"));
        }

        return createBranchService.executeCreateBranch(branchRequest);
    }

}
