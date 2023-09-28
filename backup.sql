--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: tb_accommodation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_accommodation (
    id bigint NOT NULL,
    cleaning_status integer,
    name character varying(255),
    observation character varying(255),
    occupation_status integer
);


ALTER TABLE public.tb_accommodation OWNER TO postgres;

--
-- Name: tb_accommodation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_accommodation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_accommodation_id_seq OWNER TO postgres;

--
-- Name: tb_accommodation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_accommodation_id_seq OWNED BY public.tb_accommodation.id;


--
-- Name: tb_extra_services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_extra_services (
    extra_bed boolean,
    extra_cleaning boolean,
    extra_cradle boolean,
    accommodation_id bigint NOT NULL
);


ALTER TABLE public.tb_extra_services OWNER TO postgres;

--
-- Name: tb_reservation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_reservation (
    id bigint NOT NULL,
    accommodation_id_reference bigint,
    adults integer NOT NULL,
    babies integer NOT NULL,
    checkin date,
    checkout date,
    child integer NOT NULL,
    has_pet boolean,
    holder_name character varying(255),
    is_active boolean,
    accommodation_id bigint
);


ALTER TABLE public.tb_reservation OWNER TO postgres;

--
-- Name: tb_reservation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_reservation_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_reservation_seq OWNER TO postgres;

--
-- Name: tb_accommodation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_accommodation ALTER COLUMN id SET DEFAULT nextval('public.tb_accommodation_id_seq'::regclass);


--
-- Data for Name: tb_accommodation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_accommodation (id, cleaning_status, name, observation, occupation_status) FROM stdin;
3	1	Chalé 03		2
8	1	Chalé 08		2
1	1	Chalé 01		2
2	1	Chalé 02		2
4	1	Chalé 04	Abelhas	2
5	1	Chalé 05		2
6	1	Chalé 06		2
7	1	Chalé 07		2
9	1	Chalé 09		2
10	1	Chalé 10		2
11	1	Chalé 11		2
12	1	Chalé 12		2
\.


--
-- Data for Name: tb_extra_services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_extra_services (extra_bed, extra_cleaning, extra_cradle, accommodation_id) FROM stdin;
f	f	f	1
f	f	f	2
f	f	f	5
f	f	f	6
f	f	f	7
f	f	f	9
f	f	f	10
f	f	f	11
f	f	f	12
t	t	f	4
t	t	t	3
t	f	t	8
\.


--
-- Data for Name: tb_reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_reservation (id, accommodation_id_reference, adults, babies, checkin, checkout, child, has_pet, holder_name, is_active, accommodation_id) FROM stdin;
102	1	4	0	2023-09-07	2023-09-10	0	f	Ana Maria Braga	f	1
103	2	2	0	2023-09-07	2023-09-10	2	t	Pedro Silva	f	2
104	3	2	0	2023-09-07	2023-09-10	0	f	Josinaldo Regi	f	3
105	4	2	1	2023-09-07	2023-09-10	3	t	Maria Joaquina	f	4
152	1	2	1	2023-09-07	2023-09-10	1	f	Ana Luísa	t	1
153	2	4	1	2023-09-07	2023-09-10	2	f	Carlos Eduardo	t	2
154	3	2	1	2023-09-07	2023-09-10	0	t	Sofia Beatriz	t	3
155	4	2	1	2023-09-07	2023-09-10	2	f	Lucas Gabriel	t	4
156	5	3	1	2023-09-07	2023-09-10	1	t	Laura Vitória	t	5
157	6	3	0	2023-09-07	2023-09-10	0	f	Rafaela Sofia	t	6
158	7	5	1	2023-09-07	2023-09-10	0	t	Miguel Ângelo	t	7
159	8	4	1	2023-09-07	2023-09-10	2	f	Julia Carolina	t	8
160	9	2	0	2023-09-07	2023-09-10	0	t	Leonardo Xavier	t	9
161	10	3	0	2023-09-07	2023-09-10	2	t	Isabelly Alice	t	10
162	11	3	1	2023-09-07	2023-09-10	2	f	Matheus Felipe	t	11
163	12	4	1	2023-09-07	2023-09-10	2	t	Beatriz Oliveira	t	12
\.


--
-- Name: tb_accommodation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_accommodation_id_seq', 12, true);


--
-- Name: tb_reservation_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_reservation_seq', 201, true);


--
-- Name: tb_accommodation tb_accommodation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_accommodation
    ADD CONSTRAINT tb_accommodation_pkey PRIMARY KEY (id);


--
-- Name: tb_extra_services tb_extra_services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_extra_services
    ADD CONSTRAINT tb_extra_services_pkey PRIMARY KEY (accommodation_id);


--
-- Name: tb_reservation tb_reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_reservation
    ADD CONSTRAINT tb_reservation_pkey PRIMARY KEY (id);


--
-- Name: tb_reservation fkffrlg4473c43og691sshgp8im; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_reservation
    ADD CONSTRAINT fkffrlg4473c43og691sshgp8im FOREIGN KEY (accommodation_id) REFERENCES public.tb_accommodation(id);


--
-- Name: tb_extra_services fkh35hulg992hefa6e4yiu3wgr9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_extra_services
    ADD CONSTRAINT fkh35hulg992hefa6e4yiu3wgr9 FOREIGN KEY (accommodation_id) REFERENCES public.tb_accommodation(id);


--
-- PostgreSQL database dump complete
--

