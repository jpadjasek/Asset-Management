package org.example.assetmanagement.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AssetDTO(
        UUID assetUUID,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String description,
        @NotNull @NotBlank String type,
        List<UUID> groupUUIDs) {
}
