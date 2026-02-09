package com.tecnical_test.franchise_management.franchise_core.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tecnical_test.franchise_management.franchise_core.application.dto.request.UpdateNameBranchRequest;
import com.tecnical_test.franchise_management.franchise_core.application.factory.FactoryModel;
import com.tecnical_test.franchise_management.franchise_core.application.ports.BranchRepositoryPort;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.Mockito.*;

class UpdateBranchNameServiceTest {

    private FactoryModel factoryModel;
    private BranchRepositoryPort branchRepositoryPort;
    private UpdateBranchNameService service;

    @BeforeEach
    void setUp() {
        factoryModel = mock(FactoryModel.class);
        branchRepositoryPort = mock(BranchRepositoryPort.class);
        service = new UpdateBranchNameService(factoryModel, branchRepositoryPort);
    }

    @Test
    void executeUpdateBranchName_ShouldUpdateAndReturnJsonNode() {
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        BranchModel mappedModel = BranchModel.builder().branchName("Nuevo Nombre").build();
        ResponseModel mockResponse = ResponseModel.builder().build();
        JsonNode expectedJson = JsonNodeFactory.instance.objectNode().put("status", "updated");

        when(factoryModel.buildUpdateNameDtoRequestToBranchModel(request)).thenReturn(mappedModel);
        when(branchRepositoryPort.updateNameBranch(mappedModel)).thenReturn(Mono.just(mockResponse));
        when(factoryModel.buildModelToJsonNode(mockResponse)).thenReturn(expectedJson);

        StepVerifier.create(service.executeUpdateBranchName(request))
                .expectNext(expectedJson)
                .verifyComplete();

        verify(factoryModel).buildUpdateNameDtoRequestToBranchModel(request);
        verify(branchRepositoryPort).updateNameBranch(mappedModel);
        verify(factoryModel).buildModelToJsonNode(mockResponse);
    }
}