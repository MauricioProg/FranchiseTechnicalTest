package com.tecnical_test.franchise_management.franchise_core.application.factory;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.domain.model.CreateBranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import org.springframework.stereotype.Component;


@Component
public class FactoryModel {

    public CreateBranchModel buildDtoRequestToModel(BranchRequest branchRequest) {

        return CreateBranchModel.builder()
                .branchName(branchRequest.getBranchName())
                .franchiseId(branchRequest.getFranchiseId())
                .build();

    }


    public FranchiseModel buildDtoRequestToModel(FranchiseRequest franchiseRequest) {
        return FranchiseModel.builder()
                .franchiseName(franchiseRequest.getFranchiseName())
                .build();
    }

}
