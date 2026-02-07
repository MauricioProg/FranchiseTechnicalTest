package com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper;


import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public BranchEntity branchModelToEntity(BranchModel branchModel) {
        return BranchEntity.builder()
                .name(branchModel.getBranchName())
                .build();
    }



}
