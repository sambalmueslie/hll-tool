/*************************************************************
  COMMUNITY
 *************************************************************/

CREATE SEQUENCE community_seq;
CREATE TABLE community
(
    id          BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('community_seq'::regclass),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT         NOT NULL
);

/*************************************************************
  SERVER
 *************************************************************/

CREATE SEQUENCE server_seq;
CREATE TABLE server
(
    id          BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('server_seq'::regclass),
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL
);

CREATE TABLE server_connection
(
    id       BIGINT,
    host     VARCHAR(255) NOT NULL UNIQUE,
    port     INT          NOT NULL,
    password VARCHAR(255) NOT NULL,

    CONSTRAINT fk_server_connection_server FOREIGN KEY (id)
        REFERENCES server (id) MATCH SIMPLE
);

CREATE TABLE server_monitor_settings
(
    id                      BIGINT,
    map_tracking_enabled    BOOLEAN NOT NULL,
    slot_tracking_enabled   BOOLEAN NOT NULL,
    log_tracking_enabled    BOOLEAN NOT NULL,
    player_tracking_enabled BOOLEAN NOT NULL,

    CONSTRAINT fk_server_monitor_settings_server FOREIGN KEY (id)
        REFERENCES server (id) MATCH SIMPLE
);

/*************************************************************
  MONITORING
 *************************************************************/

CREATE SEQUENCE server_map_seq;
CREATE TABLE server_map
(
    id        BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('server_map_seq'::regclass),
    name      VARCHAR(255) NOT NULL,
    server_id BIGINT       NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_server_map_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE
);

CREATE SEQUENCE server_map_stats_seq;
CREATE TABLE server_map_stats
(
    id        BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('server_map_stats_seq'::regclass),
    map_id    BIGINT NOT NULL,
    server_id BIGINT NOT NULL,
    duration  BIGINT NOT NULL,

    CONSTRAINT fk_server_map_stats_server_map FOREIGN KEY (map_id)
        REFERENCES server_map (id) MATCH SIMPLE,
    CONSTRAINT fk_server_map_stats_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE
);

CREATE SEQUENCE server_slot_stats_seq;
CREATE TABLE server_slot_stats
(
    id        BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('server_slot_stats_seq'::regclass),
    active    INT    NOT NULL,
    available INT    NOT NULL,
    server_id BIGINT NOT NULL,
    map_id    BIGINT,
    timestamp TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_server_slot_stats_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE,
    CONSTRAINT fk_server_slot_stats_server_map FOREIGN KEY (map_id)
        REFERENCES server_map (id) MATCH SIMPLE
);
