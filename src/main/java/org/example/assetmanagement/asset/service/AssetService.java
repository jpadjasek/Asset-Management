package org.example.assetmanagement.asset.service;

import lombok.extern.log4j.Log4j2;
import org.example.assetmanagement.asset.dto.AssetAssignmentDTO;
import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.asset.model.Asset;
import org.example.assetmanagement.asset.repository.AssetRepository;
import org.example.assetmanagement.exception.NotFoundException;
import org.example.assetmanagement.group.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.example.assetmanagement.asset.mapper.AssetMapper.toDto;

@Log4j2
@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final GroupService groupService;

    public AssetService(AssetRepository assetRepository, GroupService groupService) {
        this.assetRepository = assetRepository;
        this.groupService = groupService;
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

    public Asset findById(UUID assetUUID) {
        return assetRepository.findById(assetUUID)
                .orElseThrow(() -> new NotFoundException(String.format("Asset with id %s not found", assetUUID)));
    }

    public AssetAssignmentDTO assignToGroup(UUID assetUUID, AssetAssignmentDTO assetAssignmentDTO) {
        var persistedAsset = findById(assetAssignmentDTO.assetUUID());
        var persistedGroup = groupService.findById(assetAssignmentDTO.groupUUID());

        persistedAsset.addGroup(persistedGroup);

        assetRepository.save(persistedAsset);
        return AssetAssignmentDTO.merge(assetAssignmentDTO, assetUUID);
    }

    public void removeFromGroup(UUID assetUUID, UUID groupUUID) {
        var persistedAsset = findById(assetUUID);
        var persistedGroup = groupService.findById(groupUUID);

        persistedAsset.removeGroup(persistedGroup);

        assetRepository.save(persistedAsset);
    }

}
