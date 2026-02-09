package com.tecnical_test.franchise_management.franchise_core.application.factory;

import com.tecnical_test.franchise_management.franchise_core.application.dto.request.*;
import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.domain.model.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FactoryModelTest {

    private FactoryModel<Object> factoryModel;

    @BeforeEach
    void setUp() {
        factoryModel = new FactoryModel<>();
    }

    @Test
    void buildFranchiseDtoRequestToFranchiseModel_ShouldMapCorrectly() {

        FranchiseRequest request = new FranchiseRequest();
        request.setFranchiseName("Mega Tienda");

        FranchiseModel result = factoryModel.buildFranchiseDtoRequestToFranchiseModel(request);

        assertNotNull(result);
        assertEquals("Mega Tienda", result.getFranchiseName());
    }

    @Test
    void dtoResponse_ShouldCreateCorrectObject() {

        DtoResponse result = factoryModel.dtoResponse(200, "Exitoso");

        assertEquals(200, result.getStatusCode());
        assertEquals("Exitoso", result.getStatusMessage());
    }

    @Test
    void buildModelToJsonNode_ShouldReturnValidJsonNode() {

        FranchiseModel model = FranchiseModel.builder().franchiseName("Test").build();

        JsonNode node = factoryModel.buildModelToJsonNode(model);

        assertNotNull(node);
        assertEquals("Test", node.get("franchiseName").asText());
    }


    @Test
    void buildBranchDtoRequestToBranchModel_Success() {

        BranchRequest request = new BranchRequest();
        request.setBranchName("Sucursal Norte");
        request.setFranchiseId(1);

        BranchModel result = factoryModel.buildBranchDtoRequestToBranchModel(request);

        assertThat(result).isNotNull();
        assertThat(result.getBranchName()).isEqualTo("Sucursal Norte");
        assertThat(result.getFranchiseId()).isEqualTo(1);
    }

    @Test
    void buildProductDtoRequestToProductModel_ShouldHandleNumericValues() {

        ProductRequest request = new ProductRequest();
        request.setBranchId(10);
        request.setStock(50);
        request.setFranchiseId(1);
        request.setProductName("Soda");

        ProductModel result = factoryModel.buildProductDtoRequestToProductModel(request);

        assertEquals(10, result.getBranchId());
        assertEquals(50, result.getStock());
        assertEquals("Soda", result.getProductName());
    }

    @Test
    void buildProductDeleteDtoRequestToProductModel_ShouldParseStringsToInt() {

        DeleteProductRequest request = new DeleteProductRequest();
        request.setProductId(5);
        request.setBranchId("10");
        request.setFranchiseId("1");

        ProductModel result = factoryModel.buildProductDeleteDtoRequestToProductModel(request);

        assertNotNull(result);
        assertEquals(5, result.getIdProduct());
        assertEquals(10, result.getBranchId());
        assertEquals(1, result.getFranchiseId());
    }

    @Test
    void buildProductUpdateDtoRequestToProductModel_ShouldMapAllFields() {

        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductId(1);
        request.setBranchId("2");
        request.setFranchiseId("3");
        request.setStock(100);

        ProductModel result = factoryModel.buildProductUpdateDtoRequestToProductModel(request);

        assertEquals(100, result.getStock());
        assertEquals(2, result.getBranchId());
    }

    @Test
    void buildUpdateNameDtoRequestToFranchiseModel_ShouldConvertIdToString() {

        UpdateNameFranchiseRequest request = new UpdateNameFranchiseRequest();
        request.setId(500);
        request.setName("Nueva Franquicia");

        FranchiseModel result = factoryModel.buildUpdateNameDtoRequestToFranchiseModel(request);

        assertEquals("500", result.getFranchiseId());
        assertEquals("Nueva Franquicia", result.getFranchiseName());
    }

    @Test
    void buildUpdateNameDtoRequestToProductModel_ShouldMapCorrectly() {

        UpdateNameProductRequest request = new UpdateNameProductRequest();
        request.setId(99);
        request.setName("Producto Editado");
        request.setFranchiseId(1);
        request.setBranchId(2);

        ProductModel result = factoryModel.buildUpdateNameDtoRequestToProductModel(request);

        assertEquals(99, result.getIdProduct());
        assertEquals("Producto Editado", result.getProductName());
        assertEquals(1, result.getFranchiseId());
    }

    @Test
    void buildUpdateNameDtoRequestToBranchModel_ShouldMapUpdateFieldsCorrectly() {
        // 1. Arrange: Preparamos el DTO con datos de prueba
        UpdateNameBranchRequest request = new UpdateNameBranchRequest();
        request.setFranchiseId(101);
        request.setId(5);
        request.setName("Sucursal Central Editada");

        // 2. Act: Ejecutamos el mapeo
        BranchModel result = factoryModel.buildUpdateNameDtoRequestToBranchModel(request);

        // 3. Assert: Verificamos la correspondencia
        assertNotNull(result, "El modelo resultante no debería ser nulo");
        assertEquals(101, result.getFranchiseId(), "El franchiseId debe coincidir");
        assertEquals(5, result.getId(), "El ID de la sucursal debe coincidir");
        assertEquals("Sucursal Central Editada", result.getBranchName(),
                "El nombre debe mapearse de 'name' a 'branchName'");
    }

    @Test
    void buildParamDtoRequestToProductModel_ShouldMapFranchiseIdOnly() {

        int franchiseId = 999;

        ProductModel result = factoryModel.buildParamDtoRequestToProductModel(franchiseId);

        assertNotNull(result);
        assertEquals(franchiseId, result.getFranchiseId());

        
        assertNull(result.getProductName());
        assertEquals(0, result.getStock());
    }

}