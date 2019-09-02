DROP SCHEMA IF EXISTS documents;

CREATE SCHEMA library;

CREATE TABLE "library".articles (
    id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    url varchar NOT NULL,
    "name" varchar NOT NULL,
    CONSTRAINT articles_pk PRIMARY KEY (id)
);