package org.example.assetmanagement.service;

import lombok.extern.log4j.Log4j2;
import org.example.assetmanagement.dto.AssetDTO;
import org.example.assetmanagement.exception.NotFoundException;
import org.example.assetmanagement.model.Asset;
import org.example.assetmanagement.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.example.assetmanagement.mapper.AssetMapper.toDto;

@Log4j2
@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public AssetDTO create(AssetDTO assetDTO) {
        var asset = assetRepository.save(
                Asset.createAsset(assetDTO.name(),
                        assetDTO.description(),
                        assetDTO.type()));

        log.info(String.format("Created asset with UUID: %s", asset.getAssetUUID()));
        return toDto(asset);
    }

    public AssetDTO update(UUID assetUUID, AssetDTO assetDTO) {
        log.info(String.format("Updating asset with UUID: %s", assetUUID));

        var asset = findById(assetUUID);
        return toDto(assetRepository.save(
                Asset.updateAsset(asset,
                        assetDTO.name(),
                        assetDTO.description(),
                        assetDTO.type())));
    }

    public List<AssetDTO> getAllAssets() {
        return toDto(assetRepository.findAll());
    }

    public AssetDTO getByUUID(UUID assetUUID) {
        var asset = findById(assetUUID);
        return toDto(asset);
    }

    public void delete(UUID assetUUID) {
        log.info(String.format("Deleting asset with UUID: %s", assetUUID));

        var asset = findById(assetUUID);
        assetRepository.delete(asset);
    }

    private Asset findById(UUID assetUUID) {
        return assetRepository.findById(assetUUID)
                .orElseThrow(() -> new NotFoundException(String.format("Asset with id %s not found", assetUUID)));
    }

}
