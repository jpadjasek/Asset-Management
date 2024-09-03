package org.example.assetmanagement.asset.controller;

import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.asset.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public AssetDTO create(@RequestBody AssetDTO assetDTO) {
        return assetService.create(assetDTO);
    }

    @PutMapping("/{assetUUID}")
    public AssetDTO update(@PathVariable UUID assetUUID, @RequestBody AssetDTO assetDTO) {
        return assetService.update(assetUUID, assetDTO);
    }

    @GetMapping("/{assetUUID}")
    public AssetDTO getById(@PathVariable UUID assetUUID) {
        return assetService.getByUUID(assetUUID);
    }

    @DeleteMapping("/{assetUUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID assetUUID) {
        assetService.delete(assetUUID);
    }

    @RequestMapping
    public List<AssetDTO> getAll() {
        return assetService.getAllAssets();
    }
}
