package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.service.DeleteProductService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class DeleteProductHandler {

    private DeleteProductService deleteProductService;

    public DeleteProductHandler(DeleteProductService deleteProductService) {
        this.deleteProductService = deleteProductService;
    }

    public Mono<JsonNode> executeDeleteProduct(DeleteProductRequest deleteProductRequest) {

        if (deleteProductRequest.getBranchId() == null) {
            return Mono.error(new Exception("Branch Id es requerido"));
        }

        if (deleteProductRequest.getProductId() == null) {
            return Mono.error(new Exception("Product Id es requerido"));
        }

        if (deleteProductRequest.getFranchiseId() == null) {
            return  Mono.error(new Exception("Franquicia Id es requerido"));
        }

        return deleteProductService.executeDeleteProduct(deleteProductRequest);
    }

}
