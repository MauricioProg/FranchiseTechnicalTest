package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.service.PaginatedStockProductService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

@Component
public class PaginateStockProductHandler {

    private PaginatedStockProductService paginatedStockProductService;

    public PaginateStockProductHandler(PaginatedStockProductService paginatedStockProductService) {
        this.paginatedStockProductService = paginatedStockProductService;
    }

    public Flux<JsonNode> paginatedStockProduct(int franchiseId){

        if(franchiseId <= 0){
            Flux.error(new Exception("Id de franquicia invalido"));
        }

        return paginatedStockProductService.executePaginatedStockProduct(franchiseId);
    }


}
