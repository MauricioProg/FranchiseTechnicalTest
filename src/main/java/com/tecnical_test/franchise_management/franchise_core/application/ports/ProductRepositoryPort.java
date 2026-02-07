package com.tecnical_test.franchise_management.franchise_core.application.ports;

import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {

    Mono<ProductModel> createProduct(ProductModel productModel);

}
