package com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper;


import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MapperEntity<T> {

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

    public BranchModel updatedFranchiseEntityToBranchModel(FranchiseEntity franchiseEntity, BranchModel branchModel) {

        return BranchModel.builder()
                .branchName(branchModel.getBranchName())
                .franchiseId(franchiseEntity.getId())
                .id(branchModel.getId())
                .build();
    }


    public ProductModel franchiseEntityToProductModel(FranchiseEntity franchiseEntity, int idSucursal) {

        Optional<BranchEntity> branchEntity = franchiseEntity.getBranchList()
                .stream()
                .filter(branchEn -> branchEn.getId() == idSucursal).
                findFirst();

        int productId = branchEntity.get().getProductList().size() - 1;


        return ProductModel.builder()
                .idProduct(branchEntity.get().getProductList().get(productId).getId())
                .productName(branchEntity.get().getProductList().get(productId).getName())
                .franchiseId(franchiseEntity.getId())
                .branchId(branchEntity.get().getId())
                .stock(branchEntity.get().getProductList().get(productId).getStock())
                .build();
    }

    public BranchEntity branchModelToEntity(BranchModel branchModel) {
        return BranchEntity.builder()
                .name(branchModel.getBranchName())
                .build();
    }


    public ProductEntity productModelToEntity(ProductModel productModel) {
        return ProductEntity.builder()
                .id(productModel.getIdProduct())
                .name(productModel.getProductName())
                .stock(productModel.getStock())
                .build();
    }

    public ProductModel entityToProductModel(ProductEntity productEntity, int branchId, int franchiseId, String branchName) {


        return ProductModel.builder()
                .idProduct(productEntity.getId())
                .productName(productEntity.getName())
                .stock(productEntity.getStock())
                .branchId(branchId)
                .branchName(branchName)
                .franchiseId(franchiseId)
                .build();
    }

    public ProductModel franchiseEntityToUpdateStockModel(FranchiseEntity franchiseEntity, int idSucursal, int idProduct) {

        Optional<BranchEntity> branchEntity = franchiseEntity.getBranchList()
                .stream()
                .filter(branchEn -> branchEn.getId() == idSucursal).
                findFirst();

        Optional<ProductEntity> productEntity = branchEntity.get().getProductList()
                .stream()
                .filter(productEnt -> productEnt.getId() == idProduct)
                .findFirst();

        return ProductModel.builder()
                .idProduct(productEntity.get().getId())
                .productName(productEntity.get().getName())
                .franchiseId(franchiseEntity.getId())
                .branchId(branchEntity.get().getId())
                .stock(productEntity.get().getStock())
                .build();
    }


    public ResponseModel createResponseModel(T entity, int statusCode, String statusMessage) {

        return ResponseModel.builder()
                .StatusCode(statusCode)
                .StatusMessage(statusMessage)
                .data(entity)
                .build();
    }


}
