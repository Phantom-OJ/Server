begin transaction;
create table if not exists "user"
(
    uid        serial primary key,
    gid        int,
    username   varchar(45)  not null unique,
    password   varchar(100) not null,
    nickname   varchar(45),
    role       char(1)      not null default 'S',
    avatar     text         not null default 'data:image/png;base64,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',
    state_save bool         not null default true,
    state      text,
    lang       varchar(5)   not null default 'zh-CN',
    valid      bool         not null default true
);
create table if not exists "record"
(
    rid         serial primary key,
    cid         int         not null,
    uid         int         not null,
    pid         int         not null,
    result      char(3)     not null,
    description text        not null,
    space       float       not null,
    time        float       not null,
    dialect     varchar(45) not null,
    code_length int         not null,
    submit_time timestamp   not null default current_timestamp,
    valid       bool        not null default true,
    score       int         not null default 0
);
create table if not exists "problem"
(
    pid                 serial primary key,
    aid                 int   not null,
    title               text  not null,
    description         text  not null,
    full_score          int   not null,
    space_limit         float not null,
    time_limit          float not null,
    number_submit       int   not null,
    number_solve        int   not null,
    index_in_assignment int   not null,
    solution            text  not null,
    valid               bool  not null default true
);
create table if not exists "judge_database"
(
    jdid          serial primary key,
    database_path text not null,
    valid         bool not null default true
);
create table if not exists "judge_point"
(
    point_id   serial primary key,
    pid        int  not null,
    before_sql varchar(45),
    after_sql  varchar(45),
    script_id  int  not null,
    answer     text not null,
    jdid       int  not null,
    valid      bool not null default true
);
create table if not exists "permission"
(
    role      char(1) not null,
    allowance text    not null,
    valid     bool    not null default true,
    primary key (role, allowance)
);
create table if not exists "grade"
(
    uid   int  not null,
    pid   int  not null,
    score int  not null,
    valid bool not null default true,
    primary key (uid, pid)
);
create table if not exists "judge_script"
(
    script_id serial primary key,
    script    text not null,
    valid     bool not null default true
);
create table if not exists "assignment"
(
    aid                  serial primary key,
    title                text        not null,
    description          text        not null,
    start_time           timestamp   not null,
    end_time             timestamp   not null,
    status               varchar(45) not null,
    full_score           int         not null,
    sample_database_path text        not null,
    valid                bool        not null default true
);
create table if not exists "code"
(
    cid         serial primary key,
    code        text,
    valid       bool      not null default true,
    submit_time timestamp not null default current_timestamp
);
create table if not exists "tag"
(
    tid         serial primary key,
    keyword     text not null,
    description text,
    valid       bool not null default true
);
create table if not exists "assignment_group"
(
    aid   int  not null,
    gid   int  not null,
    valid bool not null default true,
    primary key (aid, gid)
);
create table if not exists "group"
(
    gid   serial primary key,
    name  varchar(45),
    valid bool not null default true
);
create table if not exists "user_group"
(
    gid   int  not null,
    uid   int  not null,
    valid bool not null default true,
    primary key (gid, uid)
);
create table if not exists "problem_tag"
(
    pid   int  not null,
    tid   int  not null,
    valid bool not null default true,
    primary key (pid, tid)
);
create table if not exists "announcement"
(
    ancmt_id      serial primary key,
    title         text      not null,
    description   text      not null,
    create_date   timestamp not null default current_timestamp,
    last_modified timestamp not null default current_timestamp,
    valid         bool      not null default true
);
commit;