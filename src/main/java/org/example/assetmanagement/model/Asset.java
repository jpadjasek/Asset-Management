package org.example.assetmanagement.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
            joinColumns = {@JoinColumn(name = "group_uuid")},
            inverseJoinColumns = {@JoinColumn(name = "asset_uuid")}
    )
    Set<Group> groups = new HashSet<>();


    public static Asset createAsset(String type, String description, String name) {
        return new Asset(UUID.randomUUID(), type, description, name, new HashSet<>());
    }

    public static Asset updateAsset(Asset asset, String type, String description, String name) {
        return new Asset(asset.getAssetUUID(), type, description, name, asset.getGroups());
    }
}
