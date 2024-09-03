package org.example.assetmanagement.group.dto;

import java.util.List;
import java.util.UUID;

public record GroupDTO(
        UUID groupUUID,
        String name,
        String description,
        List<UUID> assetUUIDs) {
}
