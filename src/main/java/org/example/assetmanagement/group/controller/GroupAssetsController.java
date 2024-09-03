package org.example.assetmanagement.group.controller;

import org.example.assetmanagement.asset.dto.AssetDTO;
import org.example.assetmanagement.group.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/groups")
public class GroupAssetsController {

    private final GroupService groupService;

    public GroupAssetsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{groupUUID}/assets")
    public List<AssetDTO> getGroupAssets(@PathVariable UUID groupUUID) {
        return groupService.getGroupAssets(groupUUID);
    }

}
