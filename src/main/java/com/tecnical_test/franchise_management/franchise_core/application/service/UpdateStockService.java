package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Service
public class UpdateStockService {

    private ProductRepositoryPort  productRepositoryPort;
    private FactoryModel factoryModel;

    public UpdateStockService(ProductRepositoryPort productRepositoryPort, FactoryModel factoryModel) {
        this.productRepositoryPort = productRepositoryPort;
        this.factoryModel = factoryModel;
    }

    public Mono<JsonNode> executeUpdateStock(UpdateProductRequest updateProductRequest){

        ProductModel productModel = factoryModel.buildProductUpdateDtoRequestToProductModel(updateProductRequest);


        return productRepositoryPort.updateStockProduct(productModel)
                .map(finalProductModel -> factoryModel.buildModelToJsonNode(finalProductModel));
    }

}
