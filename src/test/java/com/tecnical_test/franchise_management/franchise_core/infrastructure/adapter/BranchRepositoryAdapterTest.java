package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BranchRepositoryAdapterTest {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;
    private BranchRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mapper = mock(MapperEntity.class);
        franchiseRepository = mock(FranchiseRepository.class);
        adapter = new BranchRepositoryAdapter(mapper, franchiseRepository);
    }

    @Test
    void saveBranch_ShouldReturnError_WhenFranchiseDoesNotExist() {
        BranchModel model = BranchModel.builder().franchiseId(99).build();
        ResponseModel errorResponse = ResponseModel.builder().build();

        when(franchiseRepository.findById(99)).thenReturn(Mono.empty());
        when(mapper.createResponseModel(isNull(), eq(206), anyString())).thenReturn(errorResponse);

        StepVerifier.create(adapter.saveBranch(model))
                .expectNext(errorResponse)
                .verifyComplete();
    }

    @Test
    void saveBranch_ShouldSaveSuccessfully_WhenFranchiseExists() {
        BranchModel model = BranchModel.builder().franchiseId(1).branchName("Norte").build();
        FranchiseEntity existingFranchise = new FranchiseEntity();
        existingFranchise.setBranchList(new ArrayList<>());
        BranchEntity branchEntity = new BranchEntity();
        ResponseModel expectedResponse = ResponseModel.builder().build();

        when(franchiseRepository.findById((Integer) any())).thenReturn(Mono.just(existingFranchise));
        when(mapper.branchModelToEntity(any())).thenReturn(branchEntity);
        when(franchiseRepository.save(any())).thenReturn(Mono.just(existingFranchise));

        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(expectedResponse);

        StepVerifier.create(adapter.saveBranch(model))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void updateNameBranch_ShouldUpdate_WhenBranchExists() {
        BranchModel model = BranchModel.builder().franchiseId(1).id(10).branchName("Nuevo").build();
        BranchEntity bEntity = new BranchEntity();
        bEntity.setId(10);
        FranchiseEntity fEntity = new FranchiseEntity();
        fEntity.setBranchList(new ArrayList<>(List.of(bEntity)));
        ResponseModel successRes = ResponseModel.builder().build();

        when(franchiseRepository.findById((Integer) any())).thenReturn(Mono.just(fEntity));
        when(franchiseRepository.save(any())).thenReturn(Mono.just(fEntity));

        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(successRes);

        StepVerifier.create(adapter.updateNameBranch(model))
                .expectNext(successRes)
                .verifyComplete();
    }

    @Test
    void updateNameBranch_ShouldReturnError_WhenBranchNotFoundInList() {
        BranchModel model = BranchModel.builder().franchiseId(1).id(99).build();
        FranchiseEntity fEntity = new FranchiseEntity();
        fEntity.setBranchList(new ArrayList<>());
        ResponseModel errorRes = ResponseModel.builder().build();

        when(franchiseRepository.findById((Integer) any())).thenReturn(Mono.just(fEntity));

        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(errorRes);

        StepVerifier.create(adapter.updateNameBranch(model))
                .expectNext(errorRes)
                .verifyComplete();
    }
}