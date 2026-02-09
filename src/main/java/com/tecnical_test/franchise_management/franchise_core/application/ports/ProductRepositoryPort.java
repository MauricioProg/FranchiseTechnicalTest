package com.tecnical_test.franchise_management.franchise_core.application.ports;

import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {

    Mono<ResponseModel> createProduct(ProductModel productModel);

    Mono<ResponseModel> deleteProduct(ProductModel productModel);

    Mono<ResponseModel> updateStockProduct(ProductModel productModel);

    Flux<ProductModel> paginatedStockProduct(ProductModel productModel);

    Mono<ResponseModel> updateNameProduct(ProductModel productModel);

}
