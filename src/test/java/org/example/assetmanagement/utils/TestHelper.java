package org.example.assetmanagement.utils;

import org.example.assetmanagement.dto.AssetDTO;
import org.example.assetmanagement.model.Asset;
import org.example.assetmanagement.model.Group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class TestHelper {
    public static final UUID ASSET_ID = UUID.randomUUID();
    public static final String ASSET_NAME = "TEST_NAME";
    public static final String ASSET_DESCRIPTION = "TEST_DESCRIPTION";
    public static final String ASSET_TYPE = "TEST_TYPE";

    public static final UUID GROUP_ID = UUID.randomUUID();
    public static final String GROUP_NAME = "GROUP_TEST_NAME";
    public static final String GROUP_DESCRIPTION = "GROUP_DESCRIPTION";

    public static final String NEW_NAME = "NEW NAME";
    public static final String NEW_DESC = "NEW DESC";
    public static final String NEW_TYPE = "NEW TYPE";


    public static Asset prepareAssetObject() {
        return new Asset(ASSET_ID, ASSET_NAME, ASSET_DESCRIPTION, ASSET_TYPE, new HashSet<>());
    }

    public static Group prepareGroupObject() {
        return new Group(GROUP_ID, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>());
    }

    public static AssetDTO prepareAssetDTOObjectForUpdate() {
        return new AssetDTO(null, NEW_NAME, NEW_DESC, NEW_TYPE, new ArrayList<>());
    }
}
