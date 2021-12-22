CREATE SEQUENCE map_vote_server_settings_seq;
CREATE TABLE map_vote_server_settings
(
    id               BIGINT  NOT NULL PRIMARY KEY DEFAULT nextval('map_vote_server_settings_seq'::regclass),
    server_id        BIGINT  NOT NULL,
    enabled          BOOLEAN NOT NULL,
    admin_channel_id BIGINT  NOT NULL,
    user_channel_id  BIGINT  NOT NULL,

    CONSTRAINT fk_map_vote_server_settings_server FOREIGN KEY (server_id)
        REFERENCES server (id) MATCH SIMPLE
)
