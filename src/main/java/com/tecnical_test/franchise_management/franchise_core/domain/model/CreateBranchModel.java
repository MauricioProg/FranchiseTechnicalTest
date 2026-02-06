package com.tecnical_test.franchise_management.franchise_core.domain.model;

import lombok.Builder;

@Builder
public class CreateBranchModel {

    private String branchName;
    private String franchiseId;



    public boolean franchiseExist(int franchiseId) {

        if (franchiseId == 0){
            return false;
        }

        return true;
    }




}
