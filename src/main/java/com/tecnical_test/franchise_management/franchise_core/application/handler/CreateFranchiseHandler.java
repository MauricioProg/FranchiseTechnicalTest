package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateFranchiseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class CreateFranchiseHandler {

    private CreateFranchiseService createFranchiseService;

    public CreateFranchiseHandler(CreateFranchiseService createFranchiseService) {
        this.createFranchiseService = createFranchiseService;
    }

    public Mono<JsonNode> executeCreateFranchise(FranchiseRequest franchiseRequest) {

        // Buscamos sucrusal
        if (franchiseRequest.getFranchiseName() == null) {
            return Mono.error(new IllegalArgumentException("Se necesita el id franquicia a la que se desea asociar"));
        }

        return createFranchiseService.executeCreateFranchise(franchiseRequest);
    }

}
