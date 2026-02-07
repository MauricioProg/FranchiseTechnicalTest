package com.tecnical_test.franchise_management.franchise_core.domain.model;

import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Builder
@Getter
public class FranchiseModel {
    private String franchiseId;
    private String franchiseName;
    private List<BranchEntity> branchLists;

    public FranchiseModel(String franchiseId, String franchiseName, List<BranchEntity> branchLists) {
        this.franchiseId = franchiseId;
        this.franchiseName = franchiseName;
        this.branchLists = branchLists;
    }
}
