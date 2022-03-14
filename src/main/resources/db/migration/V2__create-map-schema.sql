CREATE TABLE server_settings
(
    id                    BIGINT  NOT NULL PRIMARY KEY,
    map_tracking_enabled  BOOLEAN NOT NULL,
    slot_tracking_enabled BOOLEAN NOT NULL,
    log_tracking_enabled  BOOLEAN NOT NULL,

    CONSTRAINT fk_server_settings_server FOREIGN KEY (id)
        REFERENCES server (id) MATCH SIMPLE
);

CREATE SEQUENCE map_seq;
CREATE TABLE map
(
    id        BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('map_seq'::regclass),
    name      VARCHAR(255) NOT NULL,
    server_id BIGINT       NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_map_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE
);

CREATE SEQUENCE map_stats_seq;
CREATE TABLE map_stats
(
    id        BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('map_stats_seq'::regclass),
    map_id    BIGINT NOT NULL,
    server_id BIGINT NOT NULL,
    duration  BIGINT NOT NULL,
    CONSTRAINT fk_map_stats_map FOREIGN KEY (map_id)
        REFERENCES map (id) MATCH SIMPLE,
    CONSTRAINT fk_map_stats_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE
);

CREATE SEQUENCE slot_seq;
CREATE TABLE slot
(
    id        BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('slot_seq'::regclass),
    active    INT    NOT NULL,
    available INT    NOT NULL,
    server_id BIGINT NOT NULL,
    map_id    BIGINT NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_slot_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE,
    CONSTRAINT fk_slot_map FOREIGN KEY (map_id)
        REFERENCES map (id) MATCH SIMPLE
);
