package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class PaginatedStockProductService {

    private ProductRepositoryPort  productRepositoryPort;
    private FactoryModel   factoryModel;

    public PaginatedStockProductService(ProductRepositoryPort productRepositoryPort, FactoryModel factoryModel) {
        this.productRepositoryPort = productRepositoryPort;
        this.factoryModel = factoryModel;
    }

    public Flux<JsonNode> executePaginatedStockProduct(int franchiseId){

       ProductModel productModel = factoryModel.buildParamDtoRequestToProductModel(franchiseId);

        return productRepositoryPort.paginatedStockProduct(productModel)
                .map(finalModel -> factoryModel.buildModelToJsonNode(finalModel));
    }



}
