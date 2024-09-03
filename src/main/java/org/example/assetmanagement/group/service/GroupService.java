package org.example.assetmanagement.group.service;

import lombok.extern.log4j.Log4j2;
import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.asset.mapper.AssetMapper;
import org.example.assetmanagement.exception.NotFoundException;
import org.example.assetmanagement.group.dto.GroupDTO;
import org.example.assetmanagement.group.model.Group;
import org.example.assetmanagement.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.example.assetmanagement.group.mapper.GroupMapper.toDto;


@Log4j2
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupDTO> getAllGroups() {
        return toDto(groupRepository.findAll());
    }

    public GroupDTO create(GroupDTO groupDTO) {
        var group = groupRepository.save(
                Group.createGroup(groupDTO.name(),
                        groupDTO.description()));
        log.info(String.format("Created group with UUID: %s", group.getGroupUUID()));
        return toDto(group);
    }

    public Group findById(UUID groupUUID) {
        return groupRepository.findById(groupUUID)
                .orElseThrow(() -> new NotFoundException(String.format("Group with id %s not found", groupUUID)));
    }

    public List<AssetDTO> getGroupAssets(UUID groupUUID) {
        var assets = findById(groupUUID).getAssets();
        return AssetMapper.toDto(assets);
    }

}
