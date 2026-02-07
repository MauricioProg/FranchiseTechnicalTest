package com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper;


import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class MapperEntity {

    public FranchiseEntity franchiseModelToEntity(FranchiseModel franchiseModel) {
        return FranchiseEntity.builder()
                .name(franchiseModel.getFranchiseName())
                .build();

    }

    public FranchiseModel franchiseEntityToModel(FranchiseEntity franchiseEntity) {
        return FranchiseModel.builder()
                .franchiseId(String.valueOf(franchiseEntity.getId()))
                .franchiseName(franchiseEntity.getName())
                .branchLists(franchiseEntity.getBranchList())
                .build();

    }

    public BranchModel franchiseEntityToBranchModel(FranchiseEntity franchiseEntity) {

        int idBranch = franchiseEntity.getBranchList().size() - 1;

        return BranchModel.builder()
                .branchName(franchiseEntity.getBranchList().get(idBranch).getName())
                .franchiseId(franchiseEntity.getId())
                .id(franchiseEntity.getBranchList().get(idBranch).getId())
                .build();
    }


    public ProductModel franchiseEntityToProductModel(FranchiseEntity franchiseEntity, int idSucursal) {

        int idProduct = franchiseEntity.getBranchList().get(idSucursal).getProductList().size() - 1;
        BranchEntity branchEntity = franchiseEntity.getBranchList().get(idSucursal);

        return ProductModel.builder()
                .idProduct(idProduct)
                .productName(branchEntity.getProductList().get(idProduct).getName())
                .franchiseId(franchiseEntity.getId())
                .branchId(branchEntity.getId())
                .stock(branchEntity.getProductList().get(idProduct).getStock())
                .build();
    }

    public BranchEntity branchModelToEntity(BranchModel branchModel) {
        return BranchEntity.builder()
                .name(branchModel.getBranchName())
                .build();
    }


    public ProductEntity productModelToEntity(ProductModel productModel) {
        return ProductEntity.builder()
                .id(productModel.getIdProduct() )
                .name(productModel.getProductName())
                .stock(productModel.getStock())
                .build();
    }



}
