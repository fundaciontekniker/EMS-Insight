


CREATE TABLE gcmregistration (
    gcmregistration_id bigint NOT NULL,
    registration_id text NOT NULL,
    enabled integer DEFAULT 1
);

ALTER TABLE ems.gcmregistration OWNER TO livinglab;


CREATE SEQUENCE gcmregistration_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE  ems.gcmregistration_id_seq OWNER TO livinglab;


ALTER SEQUENCE gcmregistration_id_seq OWNED BY gcmregistration.gcmregistration_id;

ALTER TABLE ONLY gcmregistration
    ADD CONSTRAINT gcmregistration_pk PRIMARY KEY (gcmregistration_id);


