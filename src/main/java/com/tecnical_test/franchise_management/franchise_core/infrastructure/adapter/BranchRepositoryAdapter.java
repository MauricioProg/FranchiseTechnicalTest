package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.application.ports.BranchRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class BranchRepositoryAdapter implements BranchRepositoryPort {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;

    public BranchRepositoryAdapter(MapperEntity mapper, FranchiseRepository franchiseRepository) {
        this.mapper = mapper;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Mono<ResponseModel> saveBranch(BranchModel branchModel) {


        return franchiseRepository.findById(Integer.valueOf(branchModel.getFranchiseId()))
                .flatMap(existingFranchise -> {

                    if (existingFranchise.getBranchList() == null) {
                        existingFranchise.setBranchList(new ArrayList<>());
                    }

                    BranchEntity newBranch = mapper.branchModelToEntity(branchModel);

                    newBranch.setId(existingFranchise.getBranchList().size() + 1);

                    existingFranchise.getBranchList().add(newBranch);

                    return franchiseRepository.save(existingFranchise)
                            .map(finalEntity -> mapper.createResponseModel(finalEntity, 200, AppConstants.BRANCH_SUCCESS_CREATE));
                })
                .switchIfEmpty(Mono.just( mapper.createResponseModel(null, 206, "Franquicia No Encontrada")));

    }

    @Override
    public Mono<ResponseModel> updateNameBranch(BranchModel branchModel) {

        return franchiseRepository.findById(Integer.valueOf(branchModel.getFranchiseId()))
                .flatMap(existingFranchise -> {

                    if (existingFranchise.getBranchList() == null) {
                        return Mono.just(mapper.createResponseModel(existingFranchise, 206, "Franquicia no cuenta con sucursales registradas"));
                    }

                    return Mono.justOrEmpty(existingFranchise.getBranchList().stream()
                                    .filter(branchEnt -> branchEnt.getId() == branchModel.getId())
                                    .findFirst())
                            .flatMap(branchEntity -> {

                                branchEntity.setName(branchModel.getBranchName());

                                return franchiseRepository.save(existingFranchise)
                                        .map(savedFranchise -> mapper.createResponseModel(savedFranchise, 200, AppConstants.BRANCH_SUCCESS_UPDATED));
                            })

                            .switchIfEmpty(Mono.just(mapper.createResponseModel(existingFranchise, 206, AppConstants.BRANCH_UNEXIST)));
                })
                .switchIfEmpty(Mono.just(mapper.createResponseModel(branchModel, 200 , AppConstants.FRANCHISE_UNEXIST)));
    }
}
