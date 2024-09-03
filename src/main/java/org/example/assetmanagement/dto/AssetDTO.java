package org.example.assetmanagement.dto;

import java.util.List;
import java.util.UUID;

public record AssetDTO(
        UUID assetUUID,
        String name,
        String description,
        String type,
        List<UUID> groupUUIDs) {
}
