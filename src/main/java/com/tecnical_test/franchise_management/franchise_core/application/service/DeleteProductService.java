package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.DeleteProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Service
public class DeleteProductService {

    private FactoryModel factoryModel;
    private ProductRepositoryPort productRepositoryPort;

    public DeleteProductService(FactoryModel factoryModel, ProductRepositoryPort productRepositoryPort) {
        this.factoryModel = factoryModel;
        this.productRepositoryPort = productRepositoryPort;
    }

    public Mono<JsonNode> executeDeleteProduct(DeleteProductRequest deleteProductRequest) {

       ProductModel productModel = factoryModel.buildProductDeleteDtoRequestToProductModel(deleteProductRequest);


        return productRepositoryPort.deleteProduct(productModel)
                .map(finalProductModel -> factoryModel.buildModelToJsonNode(finalProductModel));
    }

}
