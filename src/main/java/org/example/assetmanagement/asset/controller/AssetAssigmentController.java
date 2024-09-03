package org.example.assetmanagement.asset.controller;

import org.example.assetmanagement.asset.dto.AssetAssignmentDTO;
import org.example.assetmanagement.asset.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/assets")
public class AssetAssigmentController {

    private final AssetService assetService;

    public AssetAssigmentController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/{assetUUID}/groups")
    public AssetAssignmentDTO assignAsset(@PathVariable UUID assetUUID, @Validated @RequestBody AssetAssignmentDTO assetAssignmentDTO) {
        return assetService.assignToGroup(assetUUID, assetAssignmentDTO);
    }

    @DeleteMapping("/{assetUUID}/groups/{groupUUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignAsset(@PathVariable UUID assetUUID, @PathVariable UUID groupUUID) {
        assetService.removeFromGroup(assetUUID, groupUUID);
    }
}
