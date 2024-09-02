package org.example.assetmanagement.utils;

import org.example.assetmanagement.model.Asset;
import org.example.assetmanagement.model.Group;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TestHelper {
    public static final UUID ASSET_ID = UUID.randomUUID();
    public static final String ASSET_NAME = "TEST_NAME";
    public static final String ASSET_DESCRIPTION = "TEST_DESCRIPTION";
    public static final String ASSET_TYPE = "TEST_TYPE";

    public static final UUID GROUP_ID = UUID.randomUUID();
    public static final String GROUP_NAME = "GROUP_TEST_NAME";
    public static final String GROUP_DESCRIPTION = "GROUP_DESCRIPTION";

    public static Asset prepareAssetObject() {
        return new Asset(ASSET_ID, ASSET_NAME, ASSET_DESCRIPTION, ASSET_TYPE, Set.of(prepareGroupObject()));
    }

    public static Group prepareGroupObject() {
        return new Group(GROUP_ID, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>());
    }
}
