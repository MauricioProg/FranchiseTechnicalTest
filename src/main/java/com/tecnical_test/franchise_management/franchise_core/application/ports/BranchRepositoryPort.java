package com.tecnical_test.franchise_management.franchise_core.application.ports;

import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import reactor.core.publisher.Mono;

public interface BranchRepositoryPort {

    Mono<BranchModel> saveBranch(BranchModel branchModel);

}
