package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.application.ports.BranchRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class BranchRepositoryAdapter implements BranchRepositoryPort {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;

    public BranchRepositoryAdapter(MapperEntity mapper, FranchiseRepository franchiseRepository) {
        this.mapper = mapper;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Mono<BranchModel> saveBranch(BranchModel branchModel) {

        Integer idReal = Integer.valueOf(branchModel.getFranchiseId());

        return franchiseRepository.findById(idReal)
                .flatMap(existingFranchise -> {

                    if (existingFranchise.getBranchList() == null) {
                        existingFranchise.setBranchList(new ArrayList<>());
                    }

                    BranchEntity newBranch = mapper.branchModelToEntity(branchModel);

                    newBranch.setId(existingFranchise.getBranchList().size() + 1);

                    existingFranchise.getBranchList().add(newBranch);

                    return franchiseRepository.save(existingFranchise);
                })
                .map(mapper::franchiseEntityToBranchModel);

    }
}
