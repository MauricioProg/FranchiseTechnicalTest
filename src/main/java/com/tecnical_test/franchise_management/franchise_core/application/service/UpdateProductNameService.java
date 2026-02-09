package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Service
public class UpdateProductNameService {

    private FactoryModel factoryModel;
    private ProductRepositoryPort productRepositoryPort;

    public UpdateProductNameService(FactoryModel factoryModel, ProductRepositoryPort productRepositoryPort) {
        this.factoryModel = factoryModel;
        this.productRepositoryPort = productRepositoryPort;
    }


    public Mono<JsonNode> executeUpdateProductName(UpdateNameProductRequest updateNameProductRequest){

        ProductModel productModel = factoryModel.buildUpdateNameDtoRequestToProductModel(updateNameProductRequest);

        return productRepositoryPort.updateNameProduct(productModel)
                .map(factoryModel::buildModelToJsonNode);
    }
}
