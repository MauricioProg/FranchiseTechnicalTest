package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;


import com.tecnical_test.franchise_management.franchise_core.application.ports.FranchiseRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class FranchiseRepositoryAdapter  implements FranchiseRepositoryPort {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;

    public FranchiseRepositoryAdapter(MapperEntity mapper,
                                      FranchiseRepository franchiseRepository) {
        this.mapper = mapper;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Mono<ResponseModel> saveFranchise(FranchiseModel franchiseModel) {


        return franchiseRepository.findFirstByOrderByIdDesc()
                .map(lastEntity -> lastEntity.getId() + 1)
                .defaultIfEmpty(1)
                .flatMap(nextId -> {
                    FranchiseEntity entity = mapper.franchiseModelToEntity(franchiseModel);
                    entity.setId(nextId);
                    return franchiseRepository.save(entity)
                            .map(mapper::franchiseEntityToModel);
                })
                .map(finalEntity -> mapper.createResponseModel(finalEntity,
                        AppConstants.CODE_200, AppConstants.FRANCHISE_SUCCESS_CREATE));
    }

    @Override
    public Mono<ResponseModel> updateFranchiseName(FranchiseModel franchiseModel) {

        return franchiseRepository.findById(Integer.valueOf(franchiseModel.getFranchiseId()))
                .flatMap(franchiseEntity -> {

                    if (franchiseEntity == null) {
                        return Mono.error(new NullPointerException("Franquicia no encontrada"));
                    }

                    franchiseEntity.setName(franchiseModel.getFranchiseName());

                    return franchiseRepository.save(franchiseEntity)
                            .map(finalEntity -> mapper.createResponseModel(finalEntity,
                                    AppConstants.CODE_200, AppConstants.FRANCHISE_SUCCESS_UPDATED));
                })
                .switchIfEmpty(Mono.just(mapper.createResponseModel(null, AppConstants.CODE_206, AppConstants.FRANCHISE_UNEXIST)));
    }
}
