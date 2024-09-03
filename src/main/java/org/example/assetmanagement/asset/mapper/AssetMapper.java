package org.example.assetmanagement.asset.mapper;

import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.asset.model.Asset;
import org.example.assetmanagement.group.model.Group;

import java.util.Collection;
import java.util.List;

public class AssetMapper {

    public static List<AssetDTO> toDto(Collection<Asset> assets) {
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
