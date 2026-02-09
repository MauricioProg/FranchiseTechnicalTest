package com.tecnical_test.franchise_management.franchise_core.application.ports;

import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import reactor.core.publisher.Mono;

public interface BranchRepositoryPort {

    Mono<ResponseModel> saveBranch(BranchModel branchModel);

    Mono<ResponseModel> updateNameBranch(BranchModel branchModel);
}
