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
