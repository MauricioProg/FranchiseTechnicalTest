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

        Integer idReal = Integer.valueOf(productModel.getFranchiseId());
        Integer idBranch = Integer.valueOf(productModel.getBranchId());

        return franchiseRepository.findById(idReal)
                .flatMap(existingFranchise -> {

                   BranchEntity branchEntity = existingFranchise.getBranchList().get(idBranch);

                    if (branchEntity.getProductList() == null) {
                       branchEntity.setProductList(new ArrayList<>());
                    }

                    ProductEntity productEntity = mapper.productModelToEntity(productModel);


                    productEntity.setId(existingFranchise.getBranchList().size() + 1);

                    branchEntity.getProductList().add(productEntity);

                    return franchiseRepository.save(existingFranchise);
                })
                .map(finalProductModel -> mapper.franchiseEntityToProductModel(finalProductModel, idBranch));

    }


}
