package com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper;


import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import org.springframework.stereotype.Component;

@Component
public class mapperEntity {

    public FranchiseEntity modelToEntity(FranchiseModel franchiseModel) {
        return FranchiseEntity.builder()
                .name(franchiseModel.getFranchiseName())
                .build();

    }

    public FranchiseModel entityToModel(FranchiseEntity franchiseEntity) {

        return FranchiseModel.builder()
                .franchiseName(franchiseEntity.getName())
                //Pendiente listado de sucursales
                .build();

    }


}
