package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;


import com.tecnical_test.franchise_management.franchise_core.application.ports.SaveFranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class FranchiseRepositoryAdapter  implements SaveFranchiseRepositoryPort {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;

    public FranchiseRepositoryAdapter(MapperEntity mapper,
                                      FranchiseRepository franchiseRepository) {
        this.mapper = mapper;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Mono<FranchiseModel> saveFranchise(FranchiseModel franchiseModel) {


        return franchiseRepository.findFirstByOrderByIdDesc()
                .map(lastEntity -> lastEntity.getId() + 1)
                .defaultIfEmpty(1)
                .flatMap(nextId -> {
                    FranchiseEntity entity = mapper.franchiseModelToEntity(franchiseModel);
                    entity.setId(nextId);
                    return franchiseRepository.save(entity);
                })
                .map(mapper::franchiseEntityToModel);

    }
}
