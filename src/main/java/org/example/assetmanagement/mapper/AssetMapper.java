package org.example.assetmanagement.mapper;

import org.example.assetmanagement.dto.AssetDTO;
import org.example.assetmanagement.model.Asset;
import org.example.assetmanagement.model.Group;

import java.util.List;
import java.util.function.Function;

public class AssetMapper {

    public static List<AssetDTO> toDto(List<Asset> assets) {
        return assets.stream().map(AssetMapper::toDto).toList();
    }

    public static AssetDTO toDto(Asset asset) {
        return new AssetDTO(
                asset.getAssetUUID(),
                asset.getName(),
                asset.getDescription(),
                asset.getType(),
                asset.getGroups().stream().map(
                                Group::getGroupUUID)
                        .toList());
    }
}
