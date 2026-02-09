package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FranchiseRepositoryAdapterTest {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;
    private FranchiseRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mapper = mock(MapperEntity.class);
        franchiseRepository = mock(FranchiseRepository.class);
        adapter = new FranchiseRepositoryAdapter(mapper, franchiseRepository);
    }

    @Test
    void saveFranchise_ShouldCreateWithIncrementedId() {
        FranchiseModel model = FranchiseModel.builder().franchiseName("Test").build();
        FranchiseEntity lastEntity = new FranchiseEntity();
        lastEntity.setId(10); // El siguiente debería ser 11

        FranchiseEntity newEntity = new FranchiseEntity();
        FranchiseModel finalModel = FranchiseModel.builder().build();
        ResponseModel expectedResponse = ResponseModel.builder().build();

        when(franchiseRepository.findFirstByOrderByIdDesc()).thenReturn(Mono.just(lastEntity));
        when(mapper.franchiseModelToEntity(any())).thenReturn(newEntity);
        when(franchiseRepository.save(any())).thenReturn(Mono.just(newEntity));
        when(mapper.franchiseEntityToModel(any())).thenReturn(finalModel);
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(expectedResponse);

        StepVerifier.create(adapter.saveFranchise(model))
                .expectNext(expectedResponse)
                .verifyComplete();

        verify(franchiseRepository).save(argThat(entity -> entity.getId() == 11));
    }

    @Test
    void updateFranchiseName_ShouldUpdate_WhenExists() {
        FranchiseModel model = FranchiseModel.builder().franchiseId(String.valueOf(1)).franchiseName("Nuevo").build();
        FranchiseEntity entity = new FranchiseEntity();
        ResponseModel response = ResponseModel.builder().build();

        when(franchiseRepository.findById(anyInt())).thenReturn(Mono.just(entity));
        when(franchiseRepository.save(any())).thenReturn(Mono.just(entity));
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(response);

        StepVerifier.create(adapter.updateFranchiseName(model))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_ShouldReturnError_WhenNotExists() {
        FranchiseModel model = FranchiseModel.builder().franchiseId(String.valueOf(1)).build();
        ResponseModel errorResponse = ResponseModel.builder().build();

        when(franchiseRepository.findById(anyInt())).thenReturn(Mono.empty());
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(errorResponse);

        StepVerifier.create(adapter.updateFranchiseName(model))
                .expectNext(errorResponse)
                .verifyComplete();
    }
}