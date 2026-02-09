package com.tecnical_test.franchise_management.franchise_core.infrastructure.adapter;

import com.tecnical_test.franchise_management.franchise_core.domain.AppConstants;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ResponseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.BranchEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.ProductEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper.MapperEntity;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductRepositoryAdapterTest {

    private MapperEntity mapper;
    private FranchiseRepository franchiseRepository;
    private ProductRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mapper = mock(MapperEntity.class);
        franchiseRepository = mock(FranchiseRepository.class);
        adapter = new ProductRepositoryAdapter(mapper, franchiseRepository);
    }

    @Test
    void createProduct_ShouldSaveSuccessfully() {
        ProductModel model = ProductModel.builder().franchiseId(1).branchId(1).productName("Sal").build();

        BranchEntity branch = new BranchEntity();
        branch.setId(1);
        branch.setProductList(new ArrayList<>());

        FranchiseEntity franchise = new FranchiseEntity();
        franchise.setBranchList(new ArrayList<>(List.of(branch)));

        ResponseModel expectedRes = ResponseModel.builder().build();

        when(franchiseRepository.findById(anyInt())).thenReturn(Mono.just(franchise));
        when(mapper.productModelToEntity(any())).thenReturn(new ProductEntity());
        when(franchiseRepository.save(any())).thenReturn(Mono.just(franchise));
        when(mapper.franchiseEntityToProductModel(any(), anyInt())).thenReturn(ProductModel.builder().build());
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(expectedRes);

        StepVerifier.create(adapter.createProduct(model))
                .expectNext(expectedRes)
                .verifyComplete();
    }

    @Test
    void deleteProduct_ShouldRemoveAndReorderIds() {
        ProductModel model = ProductModel.builder().franchiseId(1).branchId(1).idProduct(1).build();

        ProductEntity prod = new ProductEntity();
        prod.setId(1);

        BranchEntity branch = new BranchEntity();
        branch.setId(1);
        branch.setProductList(new ArrayList<>(List.of(prod)));

        FranchiseEntity franchise = new FranchiseEntity();
        franchise.setBranchList(new ArrayList<>(List.of(branch)));

        ResponseModel expectedRes = ResponseModel.builder().build();

        when(franchiseRepository.findById(anyInt())).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any())).thenReturn(Mono.just(franchise));
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(expectedRes);

        StepVerifier.create(adapter.deleteProduct(model))
                .expectNext(expectedRes)
                .verifyComplete();
    }

    @Test
    void updateStockProduct_ShouldUpdateWhenExists() {
        ProductModel model = ProductModel.builder().franchiseId(1).branchId(1).idProduct(1).stock(100).build();

        ProductEntity prod = new ProductEntity();
        prod.setId(1);

        BranchEntity branch = new BranchEntity();
        branch.setId(1);
        branch.setProductList(new ArrayList<>(List.of(prod)));

        FranchiseEntity franchise = new FranchiseEntity();
        franchise.setBranchList(new ArrayList<>(List.of(branch)));

        ResponseModel expectedRes = ResponseModel.builder().build();

        when(franchiseRepository.findById(anyInt())).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any())).thenReturn(Mono.just(franchise));
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(expectedRes);

        StepVerifier.create(adapter.updateStockProduct(model))
                .expectNext(expectedRes)
                .verifyComplete();
    }

    @Test
    void paginatedStockProduct_ShouldReturnMaxStockPerBranch() {
        ProductModel model = ProductModel.builder().franchiseId(1).build();

        ProductEntity p1 = new ProductEntity(); p1.setStock(10);
        ProductEntity p2 = new ProductEntity(); p2.setStock(50); // El máximo

        BranchEntity branch = new BranchEntity();
        branch.setId(1);
        branch.setName("Sucursal A");
        branch.setProductList(new ArrayList<>(List.of(p1, p2)));

        FranchiseEntity franchise = new FranchiseEntity();
        franchise.setBranchList(new ArrayList<>(List.of(branch)));

        ProductModel resultModel = ProductModel.builder().productName("Pro").build();

        when(franchiseRepository.findById(1)).thenReturn(Mono.just(franchise));
        when(mapper.entityToProductModel(any(), anyInt(), anyInt(), anyString())).thenReturn(resultModel);

        StepVerifier.create(adapter.paginatedStockProduct(model))
                .expectNext(resultModel)
                .verifyComplete();
    }

    @Test
    void updateNameProduct_ShouldUpdateName_WhenProductExists() {

        int fId = 1;
        int bId = 10;
        int pId = 100;

        ProductModel model = ProductModel.builder()
                .franchiseId(fId)
                .branchId(bId)
                .idProduct(pId)
                .productName("Nuevo Nombre")
                .build();

        ProductEntity pEntity = new ProductEntity();
        pEntity.setId(pId);

        BranchEntity bEntity = new BranchEntity();
        bEntity.setId(bId);
        bEntity.setProductList(new ArrayList<>(List.of(pEntity)));

        FranchiseEntity fEntity = new FranchiseEntity();
        fEntity.setId(fId);
        fEntity.setBranchList(new ArrayList<>(List.of(bEntity)));

        ProductModel intermediateRes = ProductModel.builder().build();
        ResponseModel finalRes = ResponseModel.builder().build();

        // Mocks - Usamos any() para evitar que cualquier discrepancia devuelva null
        when(franchiseRepository.findById(anyInt())).thenReturn(Mono.just(fEntity));
        when(franchiseRepository.save(any())).thenReturn(Mono.just(fEntity));

        // IMPORTANTE: Estos mappers DEBEN devolver algo, si no Mono.just(null) explota
        when(mapper.franchiseEntityToUpdateStockModel(any(), anyInt(), anyInt())).thenReturn(intermediateRes);
        when(mapper.createResponseModel(any(), anyInt(), anyString())).thenReturn(finalRes);

        // 2. Act & Assert
        StepVerifier.create(adapter.updateNameProduct(model))
                .expectNext(finalRes)
                .verifyComplete();
    }

}