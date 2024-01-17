DROP TABLE IF EXISTS abonent_schema.sessions CASCADE;

DROP TABLE IF EXISTS abonent_schema.abonentid CASCADE;

DROP EXTENSION IF EXISTS "uuid-ossp" CASCADE;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE abonent_schema.abonentid
(
    id    uuid DEFAULT uuid_generate_v4(),
    ctn   varchar(255) NULL,
    name  varchar(255) NULL,
    email varchar(255) NULL,
    CONSTRAINT abonentid_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX abonentid_ctn_uindex ON abonent_schema.abonentid USING btree (ctn);



CREATE TABLE abonent_schema.sessions
(
    id      serial4      NOT NULL,
    cell_id varchar(255) NULL,
    ctn     varchar(255) NULL,
    CONSTRAINT sessions_pkey PRIMARY KEY (id),
    CONSTRAINT sessions_abonentid_id_fk FOREIGN KEY (ctn) REFERENCES abonent_schema.abonentid (ctn) ON UPDATE CASCADE
);
CREATE UNIQUE INDEX sessions_ctn_uuid_uindex ON abonent_schema.sessions USING btree (cell_id, ctn);