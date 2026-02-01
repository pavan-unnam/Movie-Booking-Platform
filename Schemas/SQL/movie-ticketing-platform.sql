-- Table: public.users


-- DROP TABLE IF EXISTS public.users;


CREATE TABLE IF NOT EXISTS public.users
(
    id uuid NOT NULL,
    city character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    role smallint,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk3g1j96g94xpk3lpxl2qbl985x UNIQUE (name),
    CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT users_role_check CHECK (role >= 0 AND role <= 1)
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.users
    OWNER to postgres;


-- Table: public.theatres


-- DROP TABLE IF EXISTS public.theatres;


CREATE TABLE IF NOT EXISTS public.theatres
(
    id uuid NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp(6) without time zone,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    partner_id character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT theatres_pkey PRIMARY KEY (id),
    CONSTRAINT uk5up6oaxdfa61ok6dmhije9dun UNIQUE (name, city),
    CONSTRAINT theatres_status_check CHECK (status::text = ANY (ARRAY['PENDING_APPROVAL'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying]::text[]))
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.theatres
    OWNER to postgres;




-- Table: public.screens


-- DROP TABLE IF EXISTS public.screens;


CREATE TABLE IF NOT EXISTS public.screens
(
    id uuid NOT NULL,
    capacity integer NOT NULL,
    layout character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    theatre_id uuid,
    CONSTRAINT screens_pkey PRIMARY KEY (id),
    CONSTRAINT fkomykyk08ts7cl7rwxp5qlaear FOREIGN KEY (theatre_id)
        REFERENCES public.theatres (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.screens
    OWNER to postgres;




-- Table: public.movies


-- DROP TABLE IF EXISTS public.movies;


CREATE TABLE IF NOT EXISTS public.movies
(
    id uuid NOT NULL,
    created_at timestamp(6) without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    duration_minutes integer,
    language character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rating double precision,
    release_date date,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT movies_pkey PRIMARY KEY (id),
    CONSTRAINT ukgovm2dombrdnujupo3o7hvtep UNIQUE (title)
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.movies
    OWNER to postgres;
-- Index: idxgovm2dombrdnujupo3o7hvtep


-- DROP INDEX IF EXISTS public.idxgovm2dombrdnujupo3o7hvtep;


CREATE INDEX IF NOT EXISTS idxgovm2dombrdnujupo3o7hvtep
    ON public.movies USING btree
    (title COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: idxkdatqkkh5popwds2hqt321l1l


-- DROP INDEX IF EXISTS public.idxkdatqkkh5popwds2hqt321l1l;


CREATE INDEX IF NOT EXISTS idxkdatqkkh5popwds2hqt321l1l
    ON public.movies USING btree
    (language COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;


-- Table: public.movie_genres


-- DROP TABLE IF EXISTS public.movie_genres;


CREATE TABLE IF NOT EXISTS public.movie_genres
(
    movie_id uuid NOT NULL,
    genres character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT fk4ak9svw913jblkfgru84h2phd FOREIGN KEY (movie_id)
        REFERENCES public.movies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.movie_genres
    OWNER to postgres;


-- Table: public.showtimes


-- DROP TABLE IF EXISTS public.showtimes;


CREATE TABLE IF NOT EXISTS public.showtimes
(
    id uuid NOT NULL,
    date_time timestamp(6) without time zone,
    price numeric(38,2),
    movie_id uuid NOT NULL,
    screen_id uuid NOT NULL,
    CONSTRAINT showtimes_pkey PRIMARY KEY (id),
    CONSTRAINT fkeltpyuei1d5g3n6ikpsjwwil6 FOREIGN KEY (movie_id)
        REFERENCES public.movies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkqh5i17921jatisuwyu12ho500 FOREIGN KEY (screen_id)
        REFERENCES public.screens (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.showtimes
    OWNER to postgres;




-- Table: public.bookings


-- DROP TABLE IF EXISTS public.bookings;


CREATE TABLE IF NOT EXISTS public.bookings
(
    id uuid NOT NULL,
    seat_id character varying(255) COLLATE pg_catalog."default",
    status smallint,
    showtime_id uuid NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT bookings_pkey PRIMARY KEY (id),
    CONSTRAINT fkc7q4u7vleq90vlvy8c7lmwtyl FOREIGN KEY (showtime_id)
        REFERENCES public.showtimes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkeyog2oic85xg7hsu2je2lx3s6 FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT bookings_status_check CHECK (status >= 0 AND status <= 2)
)


TABLESPACE pg_default;


ALTER TABLE IF EXISTS public.bookings
    OWNER to postgres;