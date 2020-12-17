create table if not exists judge_database
(
    id           serial                not null
        constraint judge_database_pkey
            primary key,
    keyword      text                  not null
        constraint judge_database_keyword_key
            unique,
    database_url text     default '{"host":"localhost","start_port":12000,"end_port":12010,"image_id":12345}':: text not null,
    valid        boolean  default true not null,
    dialect      char(10) default 'pgsql'::bpchar not null
);


INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (1, 'k1', '{"host":"localhost","database":"postgres","image_id":"judgedb:2.0"}
', true, 'pgsql     ');
INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (4, 'k2', '{"host":"localhost","database":"trigger_db","image_id":"judgedb:2.0"}', true, 'pgsql     ');
INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (2, 'mysql', '{"host":"localhost","database":"filmdb","image_id":"judge-mysql:1.0"}', true, 'mysql     ');
INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (3, 'sqlite', '{"host":"localhost","database":"filmdb","image_id":"judge-mysql:1.0"}', true, 'sqlite    ');