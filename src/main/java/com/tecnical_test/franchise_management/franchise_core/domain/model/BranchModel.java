package com.tecnical_test.franchise_management.franchise_core.domain.model;

import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BranchModel {

    private int id;
    private String branchName;
    private int franchiseId;
    private List<ProductEntity> productList;



    public boolean franchiseExist(int franchiseId) {

        if (franchiseId == 0){
            return false;
        }

        return true;
    }




}
