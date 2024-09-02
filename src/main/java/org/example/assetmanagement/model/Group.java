package org.example.assetmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @Column(name = "group_uuid")
    private UUID groupUUID;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "groups")
    private Set<Asset> assets = new HashSet<>();
}
