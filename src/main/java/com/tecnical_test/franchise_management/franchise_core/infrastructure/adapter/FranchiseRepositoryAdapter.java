package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;


import com.tecnical_test.franchise_management.franchise_core.application.ports.SaveFranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.mapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.stereotype.Component;


@Component
public class FranchiseRepositoryAdapter  implements SaveFranchiseRepositoryPort {

    private mapperEntity mapper;
    private FranchiseRepository franchiseRepository;

    public FranchiseRepositoryAdapter(mapperEntity mapper,
                                      FranchiseRepository franchiseRepository) {
        this.mapper = mapper;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public void saveFranchise(FranchiseModel franchiseModel) {

       FranchiseEntity franchiseEntity = mapper.modelToEntity(franchiseModel);

        franchiseEntity = franchiseRepository.save(franchiseEntity);


        return;


    }
}
