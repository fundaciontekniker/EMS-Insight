

CREATE TABLE alarm (
    alarm_id bigint NOT NULL,
    alarm_code text NOT NULL,
    alarm_desc text,
    alarm_message text NOT NULL,
    alarm_severity integer NOT NULL,
    alarm_type text NOT NULL,
    alarm_timespan timestamp without time zone NOT NULL,
    alarm_state text NOT NULL,
    alarm_duedate bigint,
    alarm_user bigint,
    alarm_rule bigint,
    activ numeric DEFAULT 1 NOT NULL
);



CREATE SEQUENCE alarm_alarm_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE alarm_alarm_id_seq OWNED BY alarm.alarm_id;


SELECT pg_catalog.setval('alarm_alarm_id_seq', 5, true);


CREATE TABLE cep_engine (
    ceng_id bigint NOT NULL,
    ceng_url text NOT NULL,
    ceng_desc text,
    activ numeric NOT NULL
);


CREATE SEQUENCE cep_engine_ceng_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE cep_engine_ceng_id_seq OWNED BY cep_engine.ceng_id;



SELECT pg_catalog.setval('cep_engine_ceng_id_seq', 2, true);



CREATE TABLE cep_rule (
    cep_id bigint NOT NULL,
    cep_engine bigint NOT NULL,
    cep_name text NOT NULL,
    cep_epl text NOT NULL,
    cep_listener text,
    activ numeric DEFAULT 1 NOT NULL
);



CREATE SEQUENCE cep_rule_cep_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE cep_rule_cep_id_seq OWNED BY cep_rule.cep_id;


SELECT pg_catalog.setval('cep_rule_cep_id_seq', 3, true);



CREATE TABLE cep_rule_stage (
    cep_rule_stage_id bigint NOT NULL,
    cep_id bigint NOT NULL,
    stage_id bigint NOT NULL
);



CREATE SEQUENCE cep_rule_stage_cep_rule_stage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE cep_rule_stage_cep_rule_stage_id_seq OWNED BY cep_rule_stage.cep_rule_stage_id;

SELECT pg_catalog.setval('cep_rule_stage_cep_rule_stage_id_seq', 1, false);


CREATE TABLE change_log (
    change_id bigint NOT NULL,
    change_table text NOT NULL,
    change_targetid bigint,
    change_action text NOT NULL,
    change_timestamp timestamp without time zone NOT NULL,
    change_old text,
    change_new text,
    change_col text
);


CREATE SEQUENCE change_log_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE change_log_change_id_seq OWNED BY change_log.change_id;


SELECT pg_catalog.setval('change_log_change_id_seq', 22436, true);


CREATE TABLE device (
    dev_id bigint NOT NULL,
    dev_name text NOT NULL,
    dev_uri text NOT NULL,
    dev_desc text,
    dev_status text,
    dev_info text,
    dev_localization bigint NOT NULL,
    dev_capabilities text,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE SEQUENCE device_dev_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE device_dev_id_seq OWNED BY device.dev_id;



SELECT pg_catalog.setval('device_dev_id_seq', 3, true);



CREATE SEQUENCE device_dev_localization_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SELECT pg_catalog.setval('device_dev_localization_seq', 1, false);


CREATE TABLE device_var_metadata (
    device_var_metadata_id bigint NOT NULL,
    vmd_id bigint NOT NULL,
    dev_id bigint NOT NULL
);

CREATE SEQUENCE device_var_metadata_device_var_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE device_var_metadata_device_var_metadata_id_seq OWNED BY device_var_metadata.device_var_metadata_id;


SELECT pg_catalog.setval('device_var_metadata_device_var_metadata_id_seq', 16, true);


CREATE TABLE geopoint (
    geo_id bigint NOT NULL,
    geo_loc bigint NOT NULL,
    geo_long numeric NOT NULL,
    geo_lat numeric NOT NULL,
    activ numeric NOT NULL
);


CREATE SEQUENCE geopoint_geo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE geopoint_geo_id_seq OWNED BY geopoint.geo_id;


SELECT pg_catalog.setval('geopoint_geo_id_seq', 1, false);


CREATE TABLE home (
    hi_id bigint NOT NULL,
    hi_name text NOT NULL,
    hi_endpoint text,
    hi_localization bigint NOT NULL,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE TABLE home_device (
    home_device_id bigint NOT NULL,
    hi_id bigint NOT NULL,
    dev_id bigint NOT NULL
);

CREATE SEQUENCE home_device_home_device_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE home_device_home_device_id_seq OWNED BY home_device.home_device_id;

SELECT pg_catalog.setval('home_device_home_device_id_seq', 3, true);


CREATE SEQUENCE home_hi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE home_hi_id_seq OWNED BY home.hi_id;


SELECT pg_catalog.setval('home_hi_id_seq', 1, true);


CREATE SEQUENCE home_hi_localization_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SELECT pg_catalog.setval('home_hi_localization_seq', 1, false);


CREATE TABLE home_var_metadata (
    home_var_metadata_id bigint NOT NULL,
    hi_id bigint NOT NULL,
    vmd_id bigint NOT NULL
);


CREATE SEQUENCE home_var_metadata_home_var_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE home_var_metadata_home_var_metadata_id_seq OWNED BY home_var_metadata.home_var_metadata_id;


SELECT pg_catalog.setval('home_var_metadata_home_var_metadata_id_seq', 1, false);


CREATE TABLE localization (
    loc_id bigint NOT NULL,
    loc_name text NOT NULL,
    loc_adress text,
    loc_zip text,
    loc_city text,
    loc_country text,
    loc_gridlocation text,
    activ numeric DEFAULT 1 NOT NULL
);

CREATE SEQUENCE localization_loc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE localization_loc_id_seq OWNED BY localization.loc_id;

SELECT pg_catalog.setval('localization_loc_id_seq', 1, false);

CREATE TABLE opc_node (
    opc_node_id bigint NOT NULL,
    opc_server_id bigint NOT NULL,
    opc_nodename text NOT NULL,
    opc_nmspc text NOT NULL,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE SEQUENCE opc_node_opc_node_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE opc_node_opc_node_id_seq OWNED BY opc_node.opc_node_id;


SELECT pg_catalog.setval('opc_node_opc_node_id_seq', 7, true);


CREATE TABLE opc_server (
    opc_server_id bigint NOT NULL,
    opc_url text NOT NULL,
    opc_desc text,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE SEQUENCE opc_server_opc_server_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE opc_server_opc_server_id_seq OWNED BY opc_server.opc_server_id;

SELECT pg_catalog.setval('opc_server_opc_server_id_seq', 2, true);


CREATE TABLE opc_subscription (
    opc_sub_id bigint NOT NULL,
    opc_node_id bigint NOT NULL,
    opc_vmd bigint NOT NULL,
    opc_pubint integer,
    opc_datachange text NOT NULL,
    activ numeric DEFAULT 1 NOT NULL
);

CREATE SEQUENCE opc_subscription_opc_sub_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE opc_subscription_opc_sub_id_seq OWNED BY opc_subscription.opc_sub_id;


SELECT pg_catalog.setval('opc_subscription_opc_sub_id_seq', 1, false);


CREATE TABLE stage (
    stage_id bigint NOT NULL,
    stage_owner bigint NOT NULL,
    stage_desc text,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE SEQUENCE stage_stage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE stage_stage_id_seq OWNED BY stage.stage_id;


SELECT pg_catalog.setval('stage_stage_id_seq', 1, false);

CREATE TABLE task (
    task_id bigint NOT NULL,
    task_var text,
    task_dev text,
    task_name text NOT NULL,
    task_desc text,
    task_period text NOT NULL,
    task_value text,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE TABLE task_stage (
    task_stage_id bigint NOT NULL,
    stage_id bigint NOT NULL,
    task_id bigint NOT NULL
);


CREATE SEQUENCE task_stage_task_stage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE task_stage_task_stage_id_seq OWNED BY task_stage.task_stage_id;


SELECT pg_catalog.setval('task_stage_task_stage_id_seq', 1, false);

CREATE SEQUENCE task_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE task_task_id_seq OWNED BY task.task_id;


SELECT pg_catalog.setval('task_task_id_seq', 1, false);


CREATE TABLE user_login (
    user_id bigint NOT NULL,
    user_name text NOT NULL,
    user_login text,
    user_password text,
    user_role text,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE SEQUENCE user_login_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE user_login_user_id_seq OWNED BY user_login.user_id;


SELECT pg_catalog.setval('user_login_user_id_seq', 1, false);


CREATE TABLE user_stage (
    user_stage_id bigint NOT NULL,
    user_id bigint NOT NULL,
    stage_id bigint NOT NULL
);


CREATE SEQUENCE user_stage_user_stage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE user_stage_user_stage_id_seq OWNED BY user_stage.user_stage_id;


SELECT pg_catalog.setval('user_stage_user_stage_id_seq', 1, false);


CREATE TABLE value_read (
    vr_id bigint NOT NULL,
    vr_vmd bigint NOT NULL,
    vr_value text NOT NULL,
    vr_timestamp timestamp without time zone,
    vr_quality numeric,
    activ numeric DEFAULT 1 NOT NULL
);


CREATE SEQUENCE value_read_vr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE value_read_vr_id_seq OWNED BY value_read.vr_id;


SELECT pg_catalog.setval('value_read_vr_id_seq', 22359, true);



CREATE TABLE var_metadata (
    vmd_id bigint NOT NULL,
    vmd_name text NOT NULL,
    vmd_localization bigint NOT NULL,
    vmd_phytype text,
    vmd_digtype text,
    vmd_measureunit text,
    vmd_uri text,
    vmd_access text,
    vmd_storedbd text NOT NULL,
    vmd_description text,
    activ numeric DEFAULT 1 NOT NULL
);



CREATE TABLE var_metadata_stage (
    var_metadata_stage_id bigint NOT NULL,
    vmd_id bigint NOT NULL,
    stage_id bigint NOT NULL,
    opc_pub boolean NOT NULL
);



CREATE SEQUENCE var_metadata_stage_var_metadata_stage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE var_metadata_stage_var_metadata_stage_id_seq OWNED BY var_metadata_stage.var_metadata_stage_id;


SELECT pg_catalog.setval('var_metadata_stage_var_metadata_stage_id_seq', 1, false);


CREATE SEQUENCE var_metadata_vmd_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE var_metadata_vmd_id_seq OWNED BY var_metadata.vmd_id;


SELECT pg_catalog.setval('var_metadata_vmd_id_seq', 16, true);



CREATE SEQUENCE var_metadata_vmd_localization_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;




SELECT pg_catalog.setval('var_metadata_vmd_localization_seq', 1, false);


ALTER TABLE ONLY alarm ALTER COLUMN alarm_id SET DEFAULT nextval('alarm_alarm_id_seq'::regclass);


ALTER TABLE ONLY cep_engine ALTER COLUMN ceng_id SET DEFAULT nextval('cep_engine_ceng_id_seq'::regclass);


ALTER TABLE ONLY cep_rule ALTER COLUMN cep_id SET DEFAULT nextval('cep_rule_cep_id_seq'::regclass);



ALTER TABLE ONLY cep_rule_stage ALTER COLUMN cep_rule_stage_id SET DEFAULT nextval('cep_rule_stage_cep_rule_stage_id_seq'::regclass);



ALTER TABLE ONLY change_log ALTER COLUMN change_id SET DEFAULT nextval('change_log_change_id_seq'::regclass);


--
-- TOC entry 2233 (class 2604 OID 17549)
-- Name: dev_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY device ALTER COLUMN dev_id SET DEFAULT nextval('device_dev_id_seq'::regclass);


--
-- TOC entry 2235 (class 2604 OID 17563)
-- Name: device_var_metadata_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY device_var_metadata ALTER COLUMN device_var_metadata_id SET DEFAULT nextval('device_var_metadata_device_var_metadata_id_seq'::regclass);


--
-- TOC entry 2236 (class 2604 OID 17571)
-- Name: geo_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY geopoint ALTER COLUMN geo_id SET DEFAULT nextval('geopoint_geo_id_seq'::regclass);


--
-- TOC entry 2238 (class 2604 OID 17582)
-- Name: hi_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home ALTER COLUMN hi_id SET DEFAULT nextval('home_hi_id_seq'::regclass);


--
-- TOC entry 2239 (class 2604 OID 17596)
-- Name: home_device_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home_device ALTER COLUMN home_device_id SET DEFAULT nextval('home_device_home_device_id_seq'::regclass);


--
-- TOC entry 2240 (class 2604 OID 17604)
-- Name: home_var_metadata_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home_var_metadata ALTER COLUMN home_var_metadata_id SET DEFAULT nextval('home_var_metadata_home_var_metadata_id_seq'::regclass);


--
-- TOC entry 2241 (class 2604 OID 17612)
-- Name: loc_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY localization ALTER COLUMN loc_id SET DEFAULT nextval('localization_loc_id_seq'::regclass);


--
-- TOC entry 2243 (class 2604 OID 17624)
-- Name: opc_node_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY opc_node ALTER COLUMN opc_node_id SET DEFAULT nextval('opc_node_opc_node_id_seq'::regclass);


--
-- TOC entry 2245 (class 2604 OID 17636)
-- Name: opc_server_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY opc_server ALTER COLUMN opc_server_id SET DEFAULT nextval('opc_server_opc_server_id_seq'::regclass);


--
-- TOC entry 2247 (class 2604 OID 17648)
-- Name: opc_sub_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY opc_subscription ALTER COLUMN opc_sub_id SET DEFAULT nextval('opc_subscription_opc_sub_id_seq'::regclass);


--
-- TOC entry 2249 (class 2604 OID 17660)
-- Name: stage_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY stage ALTER COLUMN stage_id SET DEFAULT nextval('stage_stage_id_seq'::regclass);


--
-- TOC entry 2251 (class 2604 OID 17672)
-- Name: task_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY task ALTER COLUMN task_id SET DEFAULT nextval('task_task_id_seq'::regclass);


--
-- TOC entry 2253 (class 2604 OID 17686)
-- Name: task_stage_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY task_stage ALTER COLUMN task_stage_id SET DEFAULT nextval('task_stage_task_stage_id_seq'::regclass);


--
-- TOC entry 2254 (class 2604 OID 17694)
-- Name: user_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY user_login ALTER COLUMN user_id SET DEFAULT nextval('user_login_user_id_seq'::regclass);


--
-- TOC entry 2256 (class 2604 OID 17710)
-- Name: user_stage_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY user_stage ALTER COLUMN user_stage_id SET DEFAULT nextval('user_stage_user_stage_id_seq'::regclass);


--
-- TOC entry 2257 (class 2604 OID 17718)
-- Name: vr_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY value_read ALTER COLUMN vr_id SET DEFAULT nextval('value_read_vr_id_seq'::regclass);


--
-- TOC entry 2259 (class 2604 OID 17730)
-- Name: vmd_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY var_metadata ALTER COLUMN vmd_id SET DEFAULT nextval('var_metadata_vmd_id_seq'::regclass);


--
-- TOC entry 2261 (class 2604 OID 17744)
-- Name: var_metadata_stage_id; Type: DEFAULT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY var_metadata_stage ALTER COLUMN var_metadata_stage_id SET DEFAULT nextval('var_metadata_stage_var_metadata_stage_id_seq'::regclass);


--
-- TOC entry 2263 (class 2606 OID 17499)
-- Name: alarm_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY alarm
    ADD CONSTRAINT alarm_pkey PRIMARY KEY (alarm_id);


--
-- TOC entry 2265 (class 2606 OID 17512)
-- Name: cep_engine_ceng_url_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY cep_engine
    ADD CONSTRAINT cep_engine_ceng_url_key UNIQUE (ceng_url);


--
-- TOC entry 2267 (class 2606 OID 17510)
-- Name: cep_engine_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY cep_engine
    ADD CONSTRAINT cep_engine_pkey PRIMARY KEY (ceng_id);


--
-- TOC entry 2269 (class 2606 OID 17524)
-- Name: cep_pk; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY cep_rule
    ADD CONSTRAINT cep_pk PRIMARY KEY (cep_id);


--
-- TOC entry 2271 (class 2606 OID 17532)
-- Name: cep_rule_stage_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY cep_rule_stage
    ADD CONSTRAINT cep_rule_stage_pkey PRIMARY KEY (cep_rule_stage_id);


--
-- TOC entry 2273 (class 2606 OID 17543)
-- Name: change_log_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY change_log
    ADD CONSTRAINT change_log_pkey PRIMARY KEY (change_id);


--
-- TOC entry 2302 (class 2606 OID 17666)
-- Name: conf_id; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY stage
    ADD CONSTRAINT conf_id PRIMARY KEY (stage_id);


--
-- TOC entry 2275 (class 2606 OID 17557)
-- Name: device_dev_name_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY device
    ADD CONSTRAINT device_dev_name_key UNIQUE (dev_name);


--
-- TOC entry 2277 (class 2606 OID 17555)
-- Name: device_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY device
    ADD CONSTRAINT device_pkey PRIMARY KEY (dev_id);


--
-- TOC entry 2279 (class 2606 OID 17565)
-- Name: device_var_metadata_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY device_var_metadata
    ADD CONSTRAINT device_var_metadata_pkey PRIMARY KEY (device_var_metadata_id);


--
-- TOC entry 2282 (class 2606 OID 17576)
-- Name: geopoint_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_pkey PRIMARY KEY (geo_id);


--
-- TOC entry 2288 (class 2606 OID 17598)
-- Name: home_device_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY home_device
    ADD CONSTRAINT home_device_pkey PRIMARY KEY (home_device_id);


--
-- TOC entry 2284 (class 2606 OID 17590)
-- Name: home_hi_name_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY home
    ADD CONSTRAINT home_hi_name_key UNIQUE (hi_name);


--
-- TOC entry 2286 (class 2606 OID 17588)
-- Name: home_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY home
    ADD CONSTRAINT home_pkey PRIMARY KEY (hi_id);


--
-- TOC entry 2291 (class 2606 OID 17606)
-- Name: home_var_metadata_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY home_var_metadata
    ADD CONSTRAINT home_var_metadata_pkey PRIMARY KEY (home_var_metadata_id);


--
-- TOC entry 2294 (class 2606 OID 17618)
-- Name: loc_id; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY localization
    ADD CONSTRAINT loc_id PRIMARY KEY (loc_id);


--
-- TOC entry 2298 (class 2606 OID 17642)
-- Name: opc_conf_pk; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY opc_server
    ADD CONSTRAINT opc_conf_pk PRIMARY KEY (opc_server_id);


--
-- TOC entry 2296 (class 2606 OID 17630)
-- Name: opc_node_pk; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY opc_node
    ADD CONSTRAINT opc_node_pk PRIMARY KEY (opc_node_id);


--
-- TOC entry 2300 (class 2606 OID 17654)
-- Name: opc_subs_pk; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY opc_subscription
    ADD CONSTRAINT opc_subs_pk PRIMARY KEY (opc_sub_id);


--
-- TOC entry 2304 (class 2606 OID 17678)
-- Name: task_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_pkey PRIMARY KEY (task_id);


--
-- TOC entry 2308 (class 2606 OID 17688)
-- Name: task_stage_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY task_stage
    ADD CONSTRAINT task_stage_pkey PRIMARY KEY (task_stage_id);


--
-- TOC entry 2306 (class 2606 OID 17680)
-- Name: task_task_name_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_task_name_key UNIQUE (task_name);


--
-- TOC entry 2310 (class 2606 OID 17700)
-- Name: user_login_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY user_login
    ADD CONSTRAINT user_login_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2312 (class 2606 OID 17704)
-- Name: user_login_user_login_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY user_login
    ADD CONSTRAINT user_login_user_login_key UNIQUE (user_login);


--
-- TOC entry 2314 (class 2606 OID 17702)
-- Name: user_login_user_name_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY user_login
    ADD CONSTRAINT user_login_user_name_key UNIQUE (user_name);


--
-- TOC entry 2316 (class 2606 OID 17712)
-- Name: user_stage_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY user_stage
    ADD CONSTRAINT user_stage_pkey PRIMARY KEY (user_stage_id);


--
-- TOC entry 2318 (class 2606 OID 17724)
-- Name: value_read_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY value_read
    ADD CONSTRAINT value_read_pkey PRIMARY KEY (vr_id);


--
-- TOC entry 2324 (class 2606 OID 17746)
-- Name: var_metada_stage_pkey; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY var_metadata_stage
    ADD CONSTRAINT var_metada_stage_pkey PRIMARY KEY (var_metadata_stage_id);


--
-- TOC entry 2320 (class 2606 OID 17738)
-- Name: var_metadata_vmd_name_key; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY var_metadata
    ADD CONSTRAINT var_metadata_vmd_name_key UNIQUE (vmd_name);


--
-- TOC entry 2322 (class 2606 OID 17736)
-- Name: varmetadata_pk; Type: CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

ALTER TABLE ONLY var_metadata
    ADD CONSTRAINT varmetadata_pk PRIMARY KEY (vmd_id);


--
-- TOC entry 2280 (class 1259 OID 17852)
-- Name: device_varmetadata_unique; Type: INDEX; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

CREATE UNIQUE INDEX device_varmetadata_unique ON device_var_metadata USING btree (dev_id, vmd_id);


--
-- TOC entry 2289 (class 1259 OID 17853)
-- Name: home_device_unique; Type: INDEX; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

CREATE UNIQUE INDEX home_device_unique ON home_device USING btree (hi_id, dev_id);


--
-- TOC entry 2292 (class 1259 OID 17854)
-- Name: home_varmetadata_unique; Type: INDEX; Schema: eefrmwrk; Owner: soa4ami; Tablespace: 
--

CREATE UNIQUE INDEX home_varmetadata_unique ON home_var_metadata USING btree (hi_id, vmd_id);


--
-- TOC entry 2325 (class 2606 OID 17747)
-- Name: cep_rule_cep_engine_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY cep_rule
    ADD CONSTRAINT cep_rule_cep_engine_fkey FOREIGN KEY (cep_engine) REFERENCES cep_engine(ceng_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2326 (class 2606 OID 17752)
-- Name: cep_rule_stage_cep_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY cep_rule_stage
    ADD CONSTRAINT cep_rule_stage_cep_id_fkey FOREIGN KEY (cep_id) REFERENCES cep_rule(cep_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2327 (class 2606 OID 17792)
-- Name: cep_rule_stage_stage_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY cep_rule_stage
    ADD CONSTRAINT cep_rule_stage_stage_id_fkey FOREIGN KEY (stage_id) REFERENCES stage(stage_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2328 (class 2606 OID 17757)
-- Name: device_varmetadata_dev_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY device_var_metadata
    ADD CONSTRAINT device_varmetadata_dev_id_fkey FOREIGN KEY (dev_id) REFERENCES device(dev_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2329 (class 2606 OID 17827)
-- Name: device_varmetadata_vmd_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY device_var_metadata
    ADD CONSTRAINT device_varmetadata_vmd_id_fkey FOREIGN KEY (vmd_id) REFERENCES var_metadata(vmd_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2330 (class 2606 OID 17777)
-- Name: geopoint_loc_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_loc_id_fkey FOREIGN KEY (geo_loc) REFERENCES localization(loc_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2331 (class 2606 OID 17762)
-- Name: home_device_dev_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home_device
    ADD CONSTRAINT home_device_dev_id_fkey FOREIGN KEY (dev_id) REFERENCES device(dev_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2332 (class 2606 OID 17767)
-- Name: home_device_hi_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home_device
    ADD CONSTRAINT home_device_hi_id_fkey FOREIGN KEY (hi_id) REFERENCES home(hi_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2333 (class 2606 OID 17772)
-- Name: home_varmetadata_hi_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home_var_metadata
    ADD CONSTRAINT home_varmetadata_hi_id_fkey FOREIGN KEY (hi_id) REFERENCES home(hi_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2334 (class 2606 OID 17832)
-- Name: home_varmetadata_vmd_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY home_var_metadata
    ADD CONSTRAINT home_varmetadata_vmd_id_fkey FOREIGN KEY (vmd_id) REFERENCES var_metadata(vmd_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2335 (class 2606 OID 17787)
-- Name: opc_node_opc_server_pk_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY opc_node
    ADD CONSTRAINT opc_node_opc_server_pk_fkey FOREIGN KEY (opc_server_id) REFERENCES opc_server(opc_server_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2336 (class 2606 OID 17782)
-- Name: opc_subscription_opc_node_pk_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY opc_subscription
    ADD CONSTRAINT opc_subscription_opc_node_pk_fkey FOREIGN KEY (opc_node_id) REFERENCES opc_node(opc_node_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2338 (class 2606 OID 17817)
-- Name: stage_stage_owner_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY stage
    ADD CONSTRAINT stage_stage_owner_fkey FOREIGN KEY (stage_owner) REFERENCES user_login(user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2339 (class 2606 OID 17797)
-- Name: task_stage_stage_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY task_stage
    ADD CONSTRAINT task_stage_stage_id_fkey FOREIGN KEY (stage_id) REFERENCES stage(stage_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2340 (class 2606 OID 17812)
-- Name: task_stage_task_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY task_stage
    ADD CONSTRAINT task_stage_task_id_fkey FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2341 (class 2606 OID 17802)
-- Name: user_stage_stage_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY user_stage
    ADD CONSTRAINT user_stage_stage_id_fkey FOREIGN KEY (stage_id) REFERENCES stage(stage_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2342 (class 2606 OID 17822)
-- Name: user_stage_user_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY user_stage
    ADD CONSTRAINT user_stage_user_id_fkey FOREIGN KEY (user_id) REFERENCES user_login(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2344 (class 2606 OID 17807)
-- Name: user_varmetada_stage_stage_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY var_metadata_stage
    ADD CONSTRAINT user_varmetada_stage_stage_id_fkey FOREIGN KEY (stage_id) REFERENCES stage(stage_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2345 (class 2606 OID 17847)
-- Name: user_varmetada_stage_vmd_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY var_metadata_stage
    ADD CONSTRAINT user_varmetada_stage_vmd_id_fkey FOREIGN KEY (vmd_id) REFERENCES var_metadata(vmd_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2343 (class 2606 OID 17842)
-- Name: valueread_vr_id_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY value_read
    ADD CONSTRAINT valueread_vr_id_fkey FOREIGN KEY (vr_vmd) REFERENCES var_metadata(vmd_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2337 (class 2606 OID 17837)
-- Name: var_metadata_opc_subscription_pk_fkey; Type: FK CONSTRAINT; Schema: eefrmwrk; Owner: soa4ami
--

ALTER TABLE ONLY opc_subscription
    ADD CONSTRAINT var_metadata_opc_subscription_pk_fkey FOREIGN KEY (opc_vmd) REFERENCES var_metadata(vmd_id) ON UPDATE CASCADE ON DELETE CASCADE;

