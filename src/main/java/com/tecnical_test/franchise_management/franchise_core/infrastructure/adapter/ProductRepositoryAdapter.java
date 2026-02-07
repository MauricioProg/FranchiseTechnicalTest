package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.application.ports.ProductRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.ProductEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
    public Mono<ProductModel> createProduct(ProductModel productModel) {

        Integer idFranchise = Integer.valueOf(productModel.getFranchiseId());
        Integer idBranch = Integer.valueOf(productModel.getBranchId());


        return franchiseRepository.findById(idFranchise)
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

                                return franchiseRepository.save(franchise);
                            });

                }).map(finalProductModel -> mapper.franchiseEntityToProductModel(finalProductModel, idBranch));
    }

    @Override
    public Mono<ProductModel> deleteProduct(ProductModel productModel) {

        Integer idFranchise = Integer.valueOf(productModel.getFranchiseId());
        Integer idBranch = Integer.valueOf(productModel.getBranchId());
        Integer idProduct = Integer.valueOf(productModel.getIdProduct());

        return franchiseRepository.findById(idFranchise)
                .flatMap(franchise -> {

                    return Mono.justOrEmpty(franchise.getBranchList().stream()
                                    .filter(branchEntity -> branchEntity.getId() == idBranch)
                                    .findFirst())
                            .flatMap(branch -> {
                                branch.getProductList().removeIf(productEntity -> productEntity.getId() == idProduct);
                                return franchiseRepository.save(franchise);
                            });

                }).map(finalProductModel -> ProductModel.builder()
                        .idProduct(idProduct)
                        .branchId(idBranch)
                        .franchiseId(idFranchise)
                        .productName("Producto Eliminado correctamente")
                        .build());

    }
}
