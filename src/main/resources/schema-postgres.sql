DROP SCHEMA IF EXISTS documents;

CREATE SCHEMA library;

CREATE TABLE documents (
    id BIGSERIAL,
    system_path VARCHAR,
    CONSTRAINT path_pk PRIMARY KEY (id)
);