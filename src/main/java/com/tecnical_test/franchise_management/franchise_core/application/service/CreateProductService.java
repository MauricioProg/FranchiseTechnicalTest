package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.ProductRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class CreateProductService {

    private FactoryModel factoryModel;
    private ProductRepositoryPort productRepositoryPort;

    public CreateProductService(FactoryModel factoryModel, ProductRepositoryPort productRepositoryPort) {
        this.factoryModel = factoryModel;
        this.productRepositoryPort = productRepositoryPort;
    }

    public Mono<JsonNode> executeCreateProduct(ProductRequest productRequest) {

        ProductModel productModel = factoryModel.buildProductDtoRequestToProductModel(productRequest);

        return productRepositoryPort.createProduct(productModel)
                .map(savedProduct -> factoryModel.buildModelToJsonNode(savedProduct));
    }
}
