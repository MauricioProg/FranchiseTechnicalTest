package com.tecnical_test.franchise_management.franchise_core.application.ports;


import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {

    Mono<FranchiseModel> saveFranchise(FranchiseModel franchiseModel);

}
