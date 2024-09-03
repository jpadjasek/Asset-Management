package org.example.assetmanagement.group.mapper;

import org.example.assetmanagement.asset.model.Asset;
import org.example.assetmanagement.group.dto.GroupDTO;
import org.example.assetmanagement.group.model.Group;

import java.util.List;

public class GroupMapper {

    public static List<GroupDTO> toDto(List<Group> groups) {
        return groups.stream().map(GroupMapper::toDto).toList();
    }

    public static GroupDTO toDto(Group group) {
        return new GroupDTO(
                group.getGroupUUID(),
                group.getName(),
                group.getDescription(),
                group.getAssets().stream().map(
                                Asset::getAssetUUID)
                        .toList());
    }
}
