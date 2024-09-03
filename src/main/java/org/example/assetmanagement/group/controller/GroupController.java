package org.example.assetmanagement.group.controller;

import org.example.assetmanagement.group.dto.GroupDTO;
import org.example.assetmanagement.group.service.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping
    public List<GroupDTO> getAll() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public GroupDTO create(@RequestBody GroupDTO assetDTO) {
        return groupService.create(assetDTO);
    }
}
