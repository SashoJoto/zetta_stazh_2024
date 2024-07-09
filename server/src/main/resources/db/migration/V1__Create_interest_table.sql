-- V1__Create_interest_table.sql
CREATE SEQUENCE interest_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE interest (
    id BIGINT NOT NULL DEFAULT nextval('interest_sequence'),
    name VARCHAR(255) NOT NULL,
    CONSTRAINT interest_pkey PRIMARY KEY (id),
    CONSTRAINT interest_name_unique UNIQUE (name)
);
