package com.tecnical_test.franchise_management.franchise_core.application.ports;


import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {

    Mono<ResponseModel> saveFranchise(FranchiseModel franchiseModel);

    Mono<ResponseModel> updateFranchiseName(FranchiseModel franchiseModel);

}
