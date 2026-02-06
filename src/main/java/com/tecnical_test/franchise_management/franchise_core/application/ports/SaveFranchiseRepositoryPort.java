package com.tecnical_test.franchise_management.franchise_core.application.ports;


import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;

public interface SaveFranchiseRepositoryPort {

    void saveFranchise(FranchiseModel franchiseModel);

}
