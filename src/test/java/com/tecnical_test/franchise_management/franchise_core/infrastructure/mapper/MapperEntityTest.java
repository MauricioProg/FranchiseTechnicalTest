package com.tecnical_test.franchise_management.franchise_core.infrastructure.mapper;

import com.tecnical_test.franchise_management.franchise_core.domain.model.*;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperEntityTest {

    private MapperEntity<Object> mapper;

    @BeforeEach
    void setUp() {
        mapper = new MapperEntity<>();
    }

    @Test
    void franchiseModelToEntity_ShouldMapCorrectly() {
        FranchiseModel model = FranchiseModel.builder().franchiseName("Franquicia Test").build();

        FranchiseEntity entity = mapper.franchiseModelToEntity(model);

        assertEquals("Franquicia Test", entity.getName());
    }

    @Test
    void franchiseEntityToBranchModel_ShouldReturnLastBranchAdded() {

        BranchEntity b1 = BranchEntity.builder().id(1).name("Sucursal 1").build();
        BranchEntity b2 = BranchEntity.builder().id(2).name("Sucursal 2").build();
        FranchiseEntity franchise = FranchiseEntity.builder()
                .id(100)
                .branchList(List.of(b1, b2))
                .build();

        BranchModel result = mapper.franchiseEntityToBranchModel(franchise);

        assertEquals(2, result.getId());
        assertEquals("Sucursal 2", result.getBranchName());
        assertEquals(100, result.getFranchiseId());
    }

    @Test
    void franchiseEntityToUpdateStockModel_ShouldFindSpecificProduct() {

        ProductEntity p1 = ProductEntity.builder().id(50).name("Producto A").stock(10).build();
        ProductEntity p2 = ProductEntity.builder().id(51).name("Producto B").stock(20).build();

        BranchEntity branch = BranchEntity.builder()
                .id(10)
                .productList(List.of(p1, p2))
                .build();

        FranchiseEntity franchise = FranchiseEntity.builder()
                .id(1)
                .branchList(List.of(branch))
                .build();

        ProductModel result = mapper.franchiseEntityToUpdateStockModel(franchise, 10, 51);

        assertNotNull(result);
        assertEquals(51, result.getIdProduct());
        assertEquals("Producto B", result.getProductName());
        assertEquals(20, result.getStock());
    }

    @Test
    void createResponseModel_ShouldWorkWithDifferentTypes() {
        String data = "Test Data";
        ResponseModel result = mapper.createResponseModel(data, 200, "Success");

        assertEquals(200, result.getStatusCode());
        assertEquals("Success", result.getStatusMessage());
        assertEquals("Test Data", result.getData());
    }

    @Test
    void franchiseEntityToModel_ShouldMapAllFields() {

        FranchiseEntity entity = FranchiseEntity.builder()
                .id(1)
                .name("Mega Corp")
                .branchList(new ArrayList<>())
                .build();

        FranchiseModel model = mapper.franchiseEntityToModel(entity);

        assertEquals("1", model.getFranchiseId());
        assertEquals("Mega Corp", model.getFranchiseName());
        assertNotNull(model.getBranchLists());
    }

    @Test
    void franchiseEntityToProductModel_ShouldReturnLastProductOfSpecificBranch() {

        ProductEntity p1 = ProductEntity.builder().id(101).name("Prod 1").stock(5).build();
        ProductEntity p2 = ProductEntity.builder().id(102).name("Prod 2").stock(10).build(); // El último

        BranchEntity branch = BranchEntity.builder()
                .id(50)
                .productList(List.of(p1, p2))
                .build();

        FranchiseEntity franchise = FranchiseEntity.builder()
                .id(1)
                .branchList(List.of(branch))
                .build();

        ProductModel result = mapper.franchiseEntityToProductModel(franchise, 50);

        assertEquals(102, result.getIdProduct());
        assertEquals("Prod 2", result.getProductName());
        assertEquals(50, result.getBranchId());
        assertEquals(1, result.getFranchiseId());
    }

    @Test
    void branchAndProductToEntity_ShouldMapNamesAndBasicFields() {

        BranchModel bModel = BranchModel.builder().branchName("Sucursal Norte").build();
        BranchEntity bEntity = mapper.branchModelToEntity(bModel);
        assertEquals("Sucursal Norte", bEntity.getName());


        ProductModel pModel = ProductModel.builder()
                .idProduct(99)
                .productName("Laptop")
                .stock(50)
                .build();
        ProductEntity pEntity = mapper.productModelToEntity(pModel);

        assertEquals(99, pEntity.getId());
        assertEquals("Laptop", pEntity.getName());
        assertEquals(50, pEntity.getStock());
    }

    @Test
    void entityToProductModel_ShouldMapAllContextFields() {
        
        ProductEntity entity = ProductEntity.builder()
                .id(777)
                .name("Producto Premium")
                .stock(100)
                .build();

        int branchId = 10;
        int franchiseId = 1;
        String branchName = "Sucursal Central";

        ProductModel result = mapper.entityToProductModel(entity, branchId, franchiseId, branchName);

        assertAll("Validación de mapeo completo",
                () -> assertEquals(777, result.getIdProduct()),
                () -> assertEquals("Producto Premium", result.getProductName()),
                () -> assertEquals(100, result.getStock()),
                () -> assertEquals(10, result.getBranchId()),
                () -> assertEquals("Sucursal Central", result.getBranchName()),
                () -> assertEquals(1, result.getFranchiseId())
        );
    }
}