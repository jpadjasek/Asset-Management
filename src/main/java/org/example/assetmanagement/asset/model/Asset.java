package org.example.assetmanagement.asset.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.assetmanagement.group.model.Group;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "assets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Asset {

    @Id
    @Column(name = "asset_uuid")
    private UUID assetUUID;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String type;

    @ManyToMany
    @JoinTable(name = "asset_groups",
            joinColumns = @JoinColumn(name = "asset_uuid"),
            inverseJoinColumns = @JoinColumn(name = "group_uuid")
    )
    Set<Group> groups;


    public static Asset createAsset(String name, String description, String type) {
        return new Asset(UUID.randomUUID(), name, description, type, new HashSet<>());
    }

    public static Asset updateAsset(Asset asset, String name, String description, String type) {
        return new Asset(asset.getAssetUUID(), name, description, type, asset.getGroups());
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(assetUUID, asset.assetUUID) && Objects.equals(name, asset.name) && Objects.equals(description, asset.description) && Objects.equals(type, asset.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetUUID, name, description, type);
    }
}
