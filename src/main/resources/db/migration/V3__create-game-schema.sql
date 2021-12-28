/*************************************************************
  TRANSLATION
 *************************************************************/

CREATE SEQUENCE game_translation_seq;
CREATE TABLE game_translation
(
    id   BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('game_translation_seq'::regclass),
    lang VARCHAR(255) NOT NULL UNIQUE
);

CREATE SEQUENCE game_translation_entry_seq;
CREATE TABLE game_translation_entry
(
    id             BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('game_translation_entry_seq'::regclass),
    key            VARCHAR(255) NOT NULL,
    value          VARCHAR(255) NOT NULL,
    translation_id BIGINT,

    CONSTRAINT fk_game_translation_entry_game_translation FOREIGN KEY (translation_id)
        REFERENCES game_translation (id) MATCH SIMPLE
);

/*************************************************************
  NATION
 *************************************************************/

CREATE SEQUENCE game_nation_definition_seq;
CREATE TABLE game_nation_definition
(
    id  BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('game_nation_definition_seq'::regclass),
    key VARCHAR(255) NOT NULL UNIQUE
);


/*************************************************************
  MAP
 *************************************************************/

CREATE SEQUENCE game_map_definition_seq;
CREATE TABLE game_map_definition
(
    id          BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('game_map_definition_seq'::regclass),
    key         VARCHAR(255) NOT NULL UNIQUE,
    type        VARCHAR(255) NOT NULL,
    attacker_id BIGINT       NOT NULL,
    defender_id BIGINT       NOT NULL,

    CONSTRAINT fk_game_map_definition_game_nation_attacker FOREIGN KEY (attacker_id)
        REFERENCES game_nation_definition (id) MATCH SIMPLE,
    CONSTRAINT fk_game_map_definition_game_nation_defender FOREIGN KEY (defender_id)
        REFERENCES game_nation_definition (id) MATCH SIMPLE
);
