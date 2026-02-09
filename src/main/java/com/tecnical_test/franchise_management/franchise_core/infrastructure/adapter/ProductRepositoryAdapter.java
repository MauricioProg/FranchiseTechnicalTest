package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.ProductEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;

    public ProductRepositoryAdapter(MapperEntity mapper,
                                      FranchiseRepository franchiseRepository) {
        this.mapper = mapper;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Mono<ResponseModel> createProduct(ProductModel productModel) {

        Integer idBranch = Integer.valueOf(productModel.getBranchId());

        return franchiseRepository.findById(Integer.valueOf(productModel.getFranchiseId()))
                .flatMap(franchise -> {

                    return Mono.justOrEmpty(franchise.getBranchList().stream()
                                    .filter(branchEntity -> branchEntity.getId() == idBranch)
                                    .findFirst())
                            .flatMap(branch -> {

                                if (branch.getProductList() == null) {
                                    branch.setProductList(new ArrayList<>());
                                }

                                ProductEntity productEntity = mapper.productModelToEntity(productModel);
                                productEntity.setId(branch.getProductList().size() + 1);

                                branch.getProductList().add(productEntity);

                                return franchiseRepository.save(franchise)
                                        .map(savedEntity ->  mapper.franchiseEntityToProductModel(savedEntity, idBranch));
                            });

                }).map(finalProductModel -> mapper.createResponseModel(finalProductModel, 200,
                        AppConstants.PRODUCT_SUCCESS_CREATE));
    }

    @Override
    public Mono<ResponseModel> deleteProduct(ProductModel productModel) {

        return franchiseRepository.findById(productModel.getFranchiseId())
                .flatMap(franchise -> {

                    return Mono.justOrEmpty(franchise.getBranchList().stream()
                                    .filter(branchEntity -> branchEntity.getId() == productModel.getBranchId())
                                    .findFirst())
                            .flatMap(branch -> {
                                branch.getProductList().removeIf(productEntity -> productEntity.getId() == productModel.getIdProduct());

                                for (int i = 0; i < branch.getProductList().size(); i++) {
                                    branch.getProductList().get(i).setId(i + 1);
                                }

                                return franchiseRepository.save(franchise).map(
                                        savedModel ->  mapper.createResponseModel(savedModel, AppConstants.CODE_200,
                                                AppConstants.PRODUCT_SUCCESS_DELETE)
                                );
                            });
                })
                .switchIfEmpty(Mono.just(mapper.createResponseModel(null, AppConstants.CODE_200,
                        AppConstants.FRANCHISE_UNEXIST)));
    }

    @Override
    public Mono<ResponseModel> updateStockProduct(ProductModel productModel) {

        return franchiseRepository.findById(productModel.getFranchiseId())
                .flatMap(franchise -> {

                     return Mono.justOrEmpty(franchise.getBranchList().stream()
                                    .filter(branchEntity -> branchEntity.getId() == productModel.getBranchId())
                                    .findFirst()).
                             flatMap(branchEntity -> {

                                 var productOpt = branchEntity.getProductList()
                                         .stream()
                                         .filter(productEntity -> productEntity.getId() == productModel.getIdProduct())
                                         .findFirst();

                                 if (productOpt.isEmpty()) {
                                     return Mono.just(mapper.createResponseModel(null, 206, AppConstants.PRODUCT_UNEXIST));
                                 }

                                 productOpt.get().setStock(productModel.getStock());

                                 return franchiseRepository.save(franchise)
                                         .map(savedEntity -> mapper.createResponseModel(savedEntity, 200, AppConstants.PRODUCT_SUCCESS_UPDATE));
                             });
                })
                .switchIfEmpty(Mono.just(mapper.createResponseModel(null, 206, AppConstants.FRANCHISE_UNEXIST)));

    }

    @Override
    public Flux<ProductModel> paginatedStockProduct(ProductModel productModel) {
        return franchiseRepository.findById(productModel.getFranchiseId())
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getBranchList())
                        .flatMap(branch -> {

                            if (branch.getProductList() == null || branch.getProductList().isEmpty()) {
                                return Mono.empty();
                            }

                            return Mono.justOrEmpty(branch.getProductList().stream()
                                            .max(Comparator.comparingInt(ProductEntity::getStock)))
                                    .map(product -> mapper.entityToProductModel(product, branch.getId(), productModel.getFranchiseId(), branch.getName()));
                        })
                );
    }

    @Override
    public Mono<ResponseModel> updateNameProduct(ProductModel productModel) {

        return franchiseRepository.findById(productModel.getFranchiseId())
                .flatMap(franchise -> {
                    return Mono.justOrEmpty(franchise.getBranchList().stream()
                                    .filter(branchEntity -> branchEntity.getId() == productModel.getBranchId())
                                    .findFirst()).
                            flatMap(branchEntity -> {

                                var productOpt = branchEntity.getProductList()
                                        .stream()
                                        .filter(productEntity -> productEntity.getId() == productModel.getIdProduct())
                                        .findFirst();

                                if (productOpt.isEmpty()) {
                                    return Mono.just(mapper.createResponseModel(productOpt, 206, AppConstants.PRODUCT_UNEXIST));
                                }

                                productOpt.get().setName(productModel.getProductName());

                                return franchiseRepository.save(franchise)
                                        .map(savedEntity -> mapper.franchiseEntityToUpdateStockModel(savedEntity, productModel.getBranchId(), productModel.getIdProduct()));
                            })
                            .switchIfEmpty(Mono.just(mapper.createResponseModel(franchise, 206, AppConstants.FRANCHISE_UNEXIST)));

                })
                .switchIfEmpty(Mono.just(mapper.createResponseModel(null, 206, AppConstants.FRANCHISE_UNEXIST )))
                .map(finalEntity -> mapper.createResponseModel(finalEntity, 200, AppConstants.PRODUCT_SUCCESS_UPDATE));
    }
}
