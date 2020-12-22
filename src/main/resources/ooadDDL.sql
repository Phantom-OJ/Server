begin transaction;
drop table if exists "announcement", assignment, assignment_group,
    code, grade, "group", judge_database, judge_point,
    judge_script, "permission", problem, problem_tag,
    record, tag, "user", user_group,record_problem_judge_point;
create table if not exists "user"
(
    id         serial primary key,
    username   varchar(45)  not null unique,
    password   varchar(100) not null,
    nickname   varchar(45),
    role       varchar      not null default 'ROLE_STUDENT',
    avatar     text         not null default '/resource/avatar/phantom.svg',
    state_save bool         not null default true,
    state      text,
    lang       varchar(5)   not null default 'zh-CN',
    valid      bool         not null default true
);
create table if not exists "record"
(
    id          serial primary key,
    code_id     int         not null,
    user_id     int         not null,
    problem_id  int         not null,
    score       int         not null default 0,
    result      varchar(10) not null,
    space       int         not null,
    time        int         not null,
    dialect     varchar(45) not null,
    code_length int         not null,
    submit_time bigint      not null default floor(
            extract(epoch from ((current_timestamp - timestamp '1970-01-01 00:00:00') * 1000))),
    valid       bool        not null default true
);
create table if not exists "record_problem_judge_point"
(
    id                serial primary key,
    record_id         int         not null,
    problem_id        int         not null,
    judge_point_index int         not null,
    time              bigint      not null,
    space             bigint      not null,
    result            varchar(10) not null,
    description       text,
    valid             bool        not null default true
);
create table if not exists problem
(
    id                  serial  not null
        constraint problem_pkey
            primary key,
    assignment_id       integer not null,
    title               text    not null,
    description         text    not null,
    full_score          integer not null,
    space_limit         integer not null default 256,
    time_limit          integer not null default 3000,
    number_submit       integer not null default 0,
    number_solve        integer not null default 0,
    index_in_assignment integer not null,
    solution            text    not null,
    valid               boolean          default true not null,
    status              varchar          default 'private'::character varying not null,
    type                varchar(45),
    judge_script_id     int
);
create table if not exists "judge_database"
(
    id           serial primary key,
    keyword      text     not null,
    database_url text     not null default '{"host":"localhost","start_port":12000,"end_port":12010,"image_id":12345}',
    valid        bool     not null default true,
    dialect      char(10) not null default 'pgsql',
    unique (keyword)
);
create table if not exists "judge_point"
(
    id                serial primary key,
    problem_id        int      not null,
    before_sql        text,
    after_sql         text,
    answer            text     not null,
    judge_database_id int      not null,
    valid             bool     not null default true,
    dialect           char(10) not null default 'pgsql',
    judge_script_id   int
);

create table if not exists "permission"
(
    id        serial primary key,
    role      varchar not null,
    allowance text    not null,
    valid     bool    not null default true,
    unique (role, allowance)
);
create table if not exists "grade"
(
    user_id    int  not null,
    problem_id int  not null,
    score      int  not null,
    valid      bool not null default true,
    primary key (user_id, problem_id)
);
create table if not exists "judge_script"
(
    id      serial primary key,
    keyword text not null,
    script  text not null,
    valid   bool not null default true,
    unique (keyword)
);
create table if not exists "assignment"
(
    id          serial primary key,
    title       text        not null,
    description text        not null,
    start_time  bigint      not null,
    end_time    bigint      not null,
    status      varchar(45) not null default 'private',
    full_score  int         not null,
    valid       bool        not null default true
);
create table if not exists "code"
(
    id          serial primary key,
    code        text,
    code_length int         not null,
    submit_time bigint      not null default floor(
            extract(epoch from ((current_timestamp - timestamp '1970-01-01 00:00:00') * 1000))),
    valid       bool        not null default true,
    dialect     varchar(10) not null default 'pgsql'
);
create table if not exists "tag"
(
    id          serial primary key,
    keyword     text not null,
    description text,
    valid       bool not null default true
);
create table if not exists "assignment_group"
(
    assignment_id int  not null,
    group_id      int  not null,
    valid         bool not null default true,
    primary key (assignment_id, group_id)
);
create table if not exists "group"
(
    id          serial primary key,
    description varchar(45) not null,
    valid       bool        not null default true
);
create table if not exists "user_group"
(
    user_id  int  not null,
    group_id int  not null,
    valid    bool not null default true,
    primary key (user_id, group_id)
);
create table if not exists "problem_tag"
(
    problem_id int  not null,
    tag_id     int  not null,
    valid      bool not null default true,
    primary key (problem_id, tag_id)
);
create table if not exists "announcement"
(
    id            serial primary key,
    title         text   not null,
    description   text   not null,
    create_date   bigint not null default floor(
            extract(epoch from ((current_timestamp - timestamp '1970-01-01 00:00:00') * 1000))),
    last_modified bigint not null default floor(
            extract(epoch from ((current_timestamp - timestamp '1970-01-01 00:00:00') * 1000))),
    valid         bool   not null default true
);
commit;