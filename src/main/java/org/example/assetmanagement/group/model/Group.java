package org.example.assetmanagement.group.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.assetmanagement.asset.model.Asset;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "groups")
@Getter
@Setter
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

    public static Group createGroup(String name, String description) {
        return new Group(UUID.randomUUID(), name, description, new HashSet<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupUUID, group.groupUUID) && Objects.equals(name, group.name) && Objects.equals(description, group.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupUUID, name, description);
    }
}
