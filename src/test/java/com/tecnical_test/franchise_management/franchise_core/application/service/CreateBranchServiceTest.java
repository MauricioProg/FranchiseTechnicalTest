package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.BranchRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.application.service.CreateBranchService;
import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;

import static org.mockito.Mockito.*;

class CreateBranchServiceTest {

    private FactoryModel factoryModel;
    private BranchRepositoryPort branchRepositoryPort;
    private CreateBranchService service;
    private MapperEntity mapperEntity;

    @BeforeEach
    void setUp() {
        factoryModel = mock(FactoryModel.class);
        branchRepositoryPort = mock(BranchRepositoryPort.class);
        mapperEntity = mock(MapperEntity.class);

        service = new CreateBranchService(factoryModel, branchRepositoryPort);
    }

    @Test
    void executeCreateBranch_ShouldSaveAndReturnJsonNode() {

        BranchRequest request = new BranchRequest();
        BranchModel mappedModel = BranchModel.builder()
                .branchName("Sucursal Test")
                .id(1)
                .franchiseId(1)
                .build();

        ResponseModel mockResponse = ResponseModel.builder().build();

        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("status", "success");

        when(factoryModel.buildBranchDtoRequestToBranchModel(request)).thenReturn(mappedModel);

        when(branchRepositoryPort.saveBranch(mappedModel)).thenReturn(Mono.just(mockResponse));

        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeCreateBranch(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildBranchDtoRequestToBranchModel(request);
        verify(branchRepositoryPort).saveBranch(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}