CREATE TABLE IF NOT EXISTS assets
(
    asset_uuid  uuid         NOT NULL PRIMARY KEY ,
    name        varchar(100) NOT NULL,
    type        varchar(100) NOT NULL,
    description varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS groups
(
    group_uuid  uuid         NOT NULL PRIMARY KEY ,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS asset_groups
(
    asset_uuid uuid NOT NULL,
    group_uuid uuid NOT NULL,

    PRIMARY KEY (asset_uuid, group_uuid),
    FOREIGN KEY (asset_uuid) REFERENCES assets (asset_uuid),
    FOREIGN KEY (group_uuid) REFERENCES groups (group_uuid)
)