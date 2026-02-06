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
            return new Mono<JsonNode>() {
                @Override
                public void subscribe(CoreSubscriber<? super JsonNode> coreSubscriber) {

                }
            };
        }

        return createFranchiseService.executeCreateFranchise(franchiseRequest);
    }

}
