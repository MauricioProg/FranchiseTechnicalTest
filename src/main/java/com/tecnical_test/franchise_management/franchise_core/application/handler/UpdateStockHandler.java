package com.tecnical_test.franchise_management.franchise_core.application.handler;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.service.UpdateStockService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Component
public class UpdateStockHandler {

    private UpdateStockService updateStockService;

    public UpdateStockHandler(UpdateStockService updateStockService) {
        this.updateStockService = updateStockService;
    }

    public Mono<JsonNode> executeUpdateProduct(UpdateProductRequest updateProductRequest){

        if (updateProductRequest.getBranchId() == null) {
            return Mono.error(new Exception("Branch Id es requerido"));
        }

        if (updateProductRequest.getProductId() == null) {
            return Mono.error(new Exception("Product Id es requerido"));
        }

        if (updateProductRequest.getFranchiseId() == null) {
            return  Mono.error(new Exception("Franquicia Id es requerido"));
        }

        if (0 >=  updateProductRequest.getStock()) {
            return  Mono.error(new Exception("Stock debe ser mayor mayor 0"));
        }


        return updateStockService.executeUpdateStock(updateProductRequest);
    };

}
