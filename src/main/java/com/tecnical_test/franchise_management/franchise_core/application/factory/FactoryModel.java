package com.tecnical_test.franchise_management.franchise_core.application.factory;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.FranchiseRequest;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


@Component
public class FactoryModel {

    public BranchModel buildDtoRequestToModel(BranchRequest branchRequest) {

        return BranchModel.builder()
                .branchName(branchRequest.getBranchName())
                .franchiseId(Integer.parseInt(branchRequest.getFranchiseId()))
                .build();

    }


    public FranchiseModel buildDtoRequestToModel(FranchiseRequest franchiseRequest) {
        return FranchiseModel.builder()
                .franchiseName(franchiseRequest.getFranchiseName())
                .build();
    }

    

    public JsonNode buildModelToJsonNode(Object model) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(model);
    }

}
