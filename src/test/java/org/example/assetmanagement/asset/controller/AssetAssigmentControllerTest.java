package org.example.assetmanagement.asset.controller;

import io.restassured.http.ContentType;
import org.example.assetmanagement.BaseIntegrationTest;
import org.example.assetmanagement.asset.dto.AssetAssignmentDTO;
import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.asset.model.Asset;
import org.example.assetmanagement.group.model.Group;
import org.example.assetmanagement.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.example.assetmanagement.utils.TestHelper.ASSET_ID;
import static org.example.assetmanagement.utils.TestHelper.GROUP_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssetAssigmentControllerTest extends BaseIntegrationTest {
    public static final UUID SECOND_GROUP_ID = UUID.randomUUID();

    @Test
    void shouldCreateNewAssetAssigment() {
        Asset testAsset = TestHelper.prepareAssetObject();
        Group testGroup = TestHelper.prepareGroupObject();

        assetRepository.save(testAsset);
        groupRepository.save(testGroup);

        AssetAssignmentDTO assignmentDTO = new AssetAssignmentDTO(ASSET_ID, GROUP_ID);

        AssetAssignmentDTO result = given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .post("/assets/" + ASSET_ID + "/groups")
                .then()
                .statusCode(200)
                .extract()
                .as(AssetAssignmentDTO.class);

        assertNotNull(result.assetUUID());
        assertEquals(GROUP_ID, result.groupUUID());
        assertEquals(ASSET_ID, result.assetUUID());

        AssetDTO assetResult = given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .get("/assets/" + ASSET_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(AssetDTO.class);

        List<UUID> assetGroupsIds = assetResult.groupUUIDs();
        assertNotNull(assetGroupsIds);
        assertEquals(1, assetGroupsIds.size());
        assertTrue(assetGroupsIds.contains(GROUP_ID));
    }

    @Test
    void shouldCreateTwoAssignmentsAndThenRemoveAssetOne() {
        AssetAssignmentDTO assignmentDTO = new AssetAssignmentDTO(ASSET_ID, GROUP_ID);
        AssetAssignmentDTO secondAssignmentDTO = new AssetAssignmentDTO(ASSET_ID, SECOND_GROUP_ID);


        Asset testAsset = TestHelper.prepareAssetObject();
        Group testGroup = TestHelper.prepareGroupObject();
        Group secondTestGroup = TestHelper.prepareGroupObject(SECOND_GROUP_ID);

        assetRepository.save(testAsset);
        groupRepository.save(testGroup);
        groupRepository.save(secondTestGroup);

        AssetAssignmentDTO firstAssigment = given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .post("/assets/" + ASSET_ID + "/groups")
                .then()
                .statusCode(200)
                .extract()
                .as(AssetAssignmentDTO.class);

        assertNotNull(firstAssigment.assetUUID());
        assertEquals(GROUP_ID, firstAssigment.groupUUID());
        assertEquals(ASSET_ID, firstAssigment.assetUUID());

        AssetAssignmentDTO secondAssigment = given()
                .contentType(ContentType.JSON)
                .body(secondAssignmentDTO)
                .when()
                .post("/assets/" + ASSET_ID + "/groups")
                .then()
                .statusCode(200)
                .extract()
                .as(AssetAssignmentDTO.class);

        assertNotNull(secondAssigment.assetUUID());
        assertEquals(SECOND_GROUP_ID, secondAssigment.groupUUID());
        assertEquals(ASSET_ID, secondAssigment.assetUUID());

        AssetDTO beforeDelete = given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .get("/assets/" + ASSET_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(AssetDTO.class);

        List<UUID> assetGroupsIds = beforeDelete.groupUUIDs();
        assertNotNull(assetGroupsIds);
        assertEquals(2, assetGroupsIds.size());
        assertTrue(assetGroupsIds.containsAll(List.of(GROUP_ID, SECOND_GROUP_ID)));

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/assets/" + ASSET_ID + "/groups" + "/" + GROUP_ID)
                .then()
                .statusCode(204);

        AssetDTO afterDelete = given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .get("/assets/" + ASSET_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(AssetDTO.class);

        List<UUID> afterDeleteAssetGroupsIds = afterDelete.groupUUIDs();
        assertNotNull(afterDeleteAssetGroupsIds);
        assertEquals(1, afterDeleteAssetGroupsIds.size());
        assertFalse(afterDeleteAssetGroupsIds.containsAll(List.of(GROUP_ID, SECOND_GROUP_ID)));
        assertTrue(afterDeleteAssetGroupsIds.contains(SECOND_GROUP_ID));
    }

    @Test
    void shouldReturn404WhenGroupNotFound() {
        Asset testAsset = TestHelper.prepareAssetObject();

        assetRepository.save(testAsset);

        AssetAssignmentDTO assignmentDTO = new AssetAssignmentDTO(ASSET_ID, GROUP_ID);

        given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .post("/assets/" + ASSET_ID + "/groups")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturn404WhenAssetNotFound() {
        Group testGroup = TestHelper.prepareGroupObject();

        groupRepository.save(testGroup);

        AssetAssignmentDTO assignmentDTO = new AssetAssignmentDTO(ASSET_ID, GROUP_ID);

        given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .post("/assets/" + ASSET_ID + "/groups")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturn404WhenMissingRequiredFields() {
        AssetAssignmentDTO assignmentDTO = new AssetAssignmentDTO(null, null);

        given()
                .contentType(ContentType.JSON)
                .body(assignmentDTO)
                .when()
                .post("/assets/" + ASSET_ID + "/groups")
                .then()
                .statusCode(400);

    }

}