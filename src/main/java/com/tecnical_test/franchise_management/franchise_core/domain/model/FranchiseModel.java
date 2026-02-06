package com.tecnical_test.franchise_management.franchise_core.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.stream.Stream;

@Builder
@Getter
public class FranchiseModel {
    private String franchiseId;
    private String franchiseName;
    private Stream<String> branchLists;

    public FranchiseModel(String franchiseId, String franchiseName, Stream<String> branchLists) {
        this.franchiseId = franchiseId;
        this.franchiseName = franchiseName;
        this.branchLists = branchLists;
    }
}
