package org.example.assetmanagement.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record GroupDTO(
        UUID groupUUID,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String description,
        List<UUID> assetUUIDs) {
}
