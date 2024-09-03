package org.example.assetmanagement.asset.controller;

import io.restassured.http.ContentType;
import org.example.assetmanagement.BaseIntegrationTest;
import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.asset.mapper.AssetMapper;
import org.example.assetmanagement.asset.model.Asset;
import org.example.assetmanagement.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.example.assetmanagement.utils.TestHelper.ASSET_DESCRIPTION;
import static org.example.assetmanagement.utils.TestHelper.ASSET_ID;
import static org.example.assetmanagement.utils.TestHelper.ASSET_NAME;
import static org.example.assetmanagement.utils.TestHelper.ASSET_TYPE;
import static org.example.assetmanagement.utils.TestHelper.NEW_DESC;
import static org.example.assetmanagement.utils.TestHelper.NEW_NAME;
import static org.example.assetmanagement.utils.TestHelper.NEW_TYPE;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssetControllerTestIT extends BaseIntegrationTest {

    @Test
    void shouldCreateNewAsset() {
        Asset testAsset = TestHelper.prepareAssetObject();
        AssetDTO testAssetDTO = AssetMapper.toDto(testAsset);


        AssetDTO result = given()
                .contentType(ContentType.JSON)
                .body(testAssetDTO)
                .when()
                .post("/assets")
                .then()
                .statusCode(200)
                .extract()
                .as(AssetDTO.class);

        assertNotNull(result.assetUUID());
        assertEquals(ASSET_NAME, result.name());
        assertEquals(ASSET_TYPE, result.type());
        assertEquals(ASSET_DESCRIPTION, result.description());
    }

    @Test
    void shouldReturn400WhenPOSTWithoutBody() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/assets")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldUpdateExistingAsset() {
        Asset testAsset = TestHelper.prepareAssetObject();
        AssetDTO assetDTOForUpdate = TestHelper.prepareAssetDTOObjectForUpdate();

        assetRepository.save(testAsset);

        AssetDTO result = given()
                .contentType(ContentType.JSON)
                .body(assetDTOForUpdate)
                .when()
                .put("/assets/" + ASSET_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(AssetDTO.class);

        assertEquals(ASSET_ID, result.assetUUID());
        assertEquals(NEW_NAME, result.name());
        assertEquals(NEW_TYPE, result.type());
        assertEquals(NEW_DESC, result.description());
    }

    @Test
    void shouldReturn404WhenPutAndAssetDoesNotExist() {
        AssetDTO assetDTOForUpdate = TestHelper.prepareAssetDTOObjectForUpdate();

        given()
                .contentType(ContentType.JSON)
                .body(assetDTOForUpdate)
                .when()
                .put("/assets/" + ASSET_ID)
                .then()
                .statusCode(404);

    }

    @Test
    void shouldReturn400WhenPUTWithoutBody() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/assets/" + ASSET_ID)
                .then()
                .statusCode(400);
    }

    @Test
    void shouldGetListWithOneAsset() {
        Asset testAsset = TestHelper.prepareAssetObject();
        AssetDTO testAssetDTO = AssetMapper.toDto(testAsset);
        List<Asset> assets = List.of(testAsset);
        assetRepository.saveAll(assets);

        List<AssetDTO> result = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/assets")
                .then()
                .statusCode(200)
                .body(".", hasSize(1))
                .extract().body().jsonPath().getList(".", AssetDTO.class);

        assertTrue(result.contains(testAssetDTO));
    }

    @Test
    void shouldGetEmptyList() {
        List<AssetDTO> result = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/assets")
                .then()
                .statusCode(200)
                .body(".", hasSize(0))
                .extract().body().jsonPath().getList(".", AssetDTO.class);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetAssetByUUID() {
        Asset testAsset = TestHelper.prepareAssetObject();

        assetRepository.save(testAsset);

        AssetDTO result = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/assets/" + ASSET_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(AssetDTO.class);

        assertEquals(ASSET_ID, result.assetUUID());
        assertEquals(ASSET_NAME, result.name());
        assertEquals(ASSET_TYPE, result.type());
        assertEquals(ASSET_DESCRIPTION, result.description());
    }

    @Test
    void shouldReturn404WhenGetByIdAndAssetDoesNotExist() {
        AssetDTO assetDTOForUpdate = TestHelper.prepareAssetDTOObjectForUpdate();

        given()
                .contentType(ContentType.JSON)
                .body(assetDTOForUpdate)
                .when()
                .get("/assets/" + ASSET_ID)
                .then()
                .statusCode(404);
    }

    @Test
    void shouldDeleteAsset() {
        Asset testAsset = TestHelper.prepareAssetObject();

        assetRepository.save(testAsset);

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/assets/" + ASSET_ID)
                .then()
                .statusCode(204);


        assertTrue(assetRepository.findAll().isEmpty());
    }

    @Test
    void shouldReturn404WhenDeleteAndAssetDoesNotExist() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/assets/" + ASSET_ID)
                .then()
                .statusCode(404);
    }
}