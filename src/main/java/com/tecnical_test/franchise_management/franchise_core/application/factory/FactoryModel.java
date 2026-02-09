package com.tecnical_test.franchise_management.franchise_core.application.factory;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.*;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


@Component
public class FactoryModel<T> {

    public BranchModel buildBranchDtoRequestToBranchModel(BranchRequest branchRequest) {

        return BranchModel.builder()
                .branchName(String.valueOf(branchRequest.getBranchName()))
                .franchiseId(branchRequest.getFranchiseId())
                .build();

    }

    public FranchiseModel buildFranchiseDtoRequestToFranchiseModel(FranchiseRequest franchiseRequest) {
        return FranchiseModel.builder()
                .franchiseName(franchiseRequest.getFranchiseName())
                .build();
    }


    public ProductModel buildProductDtoRequestToProductModel(ProductRequest productRequest) {
        return ProductModel.builder()
                .branchId(productRequest.getBranchId())
                .franchiseId(productRequest.getFranchiseId())
                .productName(productRequest.getProductName())
                .stock(productRequest.getStock())
                .build();
    }

    public ProductModel buildProductDeleteDtoRequestToProductModel(DeleteProductRequest deleteProductRequest) {
        return ProductModel.builder()
                .idProduct(deleteProductRequest.getProductId())
                .branchId(Integer.parseInt(deleteProductRequest.getBranchId()))
                .franchiseId(Integer.parseInt(deleteProductRequest.getFranchiseId()))
                .build();
    }


    public ProductModel buildProductUpdateDtoRequestToProductModel(UpdateProductRequest updateProductRequest) {
        return ProductModel.builder()
                .idProduct(updateProductRequest.getProductId())
                .branchId(Integer.parseInt(updateProductRequest.getBranchId()))
                .franchiseId(Integer.parseInt(updateProductRequest.getFranchiseId()))
                .stock(updateProductRequest.getStock())
                .build();
    }

    public ProductModel buildParamDtoRequestToProductModel(int franchiseId) {
        return ProductModel.builder()
                .franchiseId(franchiseId)
                .build();
    }

    public FranchiseModel buildUpdateNameDtoRequestToFranchiseModel(UpdateNameFranchiseRequest updateNameFranchiseRequest) {
        return FranchiseModel.builder()
                .franchiseId(String.valueOf(updateNameFranchiseRequest.getId()))
                .franchiseName(updateNameFranchiseRequest.getName())
                .build();
    }

    public BranchModel buildUpdateNameDtoRequestToBranchModel(UpdateNameBranchRequest updateNameBranchRequest) {
        return BranchModel.builder()
                .franchiseId(updateNameBranchRequest.getFranchiseId())
                .id(updateNameBranchRequest.getId())
                .branchName(updateNameBranchRequest.getName())
                .build();
    }

    public ProductModel buildUpdateNameDtoRequestToProductModel(UpdateNameProductRequest updateNameProductRequest) {
        return ProductModel.builder()
                .franchiseId(updateNameProductRequest.getFranchiseId())
                .idProduct(updateNameProductRequest.getId())
                .productName(updateNameProductRequest.getName())
                .branchId(updateNameProductRequest.getBranchId())
                .build();
    }

    public DtoResponse dtoResponse(int statusCode, String message){
        return DtoResponse.builder()
                .StatusCode(statusCode)
                .StatusMessage(message)
                .build();

    }

    public JsonNode buildModelToJsonNode(Object model) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(model);
    }

}
