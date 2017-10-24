--liquibase formatted sql
--changeset cam:release_0.TMP_create_tables.sql

CREATE SCHEMA AD;
CREATE TABLE AD.SampleTable
(
  id uuid NOT NULL,
  someNumber INTEGER,
  CONSTRAINT sampletable_pkey PRIMARY KEY (id)
);
ALTER TABLE AD.SampleTable
  OWNER TO ad;

--rollback DROP TABLE SampleTable;