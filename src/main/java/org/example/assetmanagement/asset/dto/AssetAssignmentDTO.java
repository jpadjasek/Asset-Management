package org.example.assetmanagement.asset.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssetAssignmentDTO(UUID assetUUID, @NotNull UUID groupUUID) {

    public static AssetAssignmentDTO merge(AssetAssignmentDTO requestDTO, UUID assetUUID) {
        return new AssetAssignmentDTO(requestDTO.groupUUID, assetUUID);
    }
}
