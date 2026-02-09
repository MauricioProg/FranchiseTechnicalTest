package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.service.PaginatedStockProductService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

@Component
public class PaginateStockProductHandler {

    private PaginatedStockProductService paginatedStockProductService;
    private FactoryModel factoryModel;

    public PaginateStockProductHandler(PaginatedStockProductService paginatedStockProductService, FactoryModel factoryModel) {
        this.paginatedStockProductService = paginatedStockProductService;
        this.factoryModel = factoryModel;
    }

    public Flux<JsonNode> paginatedStockProduct(int franchiseId){

        if(franchiseId <= 0){
            return Flux.just(factoryModel.dtoResponse(AppConstants.CODE_206, AppConstants.MANDATORY_FRANCHISE_CODE))
                    .map(factoryModel::buildModelToJsonNode);
        }

        return paginatedStockProductService.executePaginatedStockProduct(franchiseId);
    }


}
