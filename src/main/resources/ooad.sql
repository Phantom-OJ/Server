begin transaction;
create table if not exists "user"
(
    id         serial primary key,
    group_id   int,
    username   varchar(45)  not null unique,
    password   varchar(100) not null,
    nickname   varchar(45),
    role       varchar      not null default 'ROLE_STUDENT',
    avatar     text         not null default 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAKYAAACmAE200ffAAAHRklEQVRoge1YS2hc1xn+/3Puuc+5I42sscaS+rDHpqIJ1BCXEto6iSHQRUExDV0EAvXWi6RdOdBCo7RddNUHxauGODF0UQpxIOmm4Jp2UYLaJoUEksWA+9B0bNV6zGie957zl390rjxVLHlGkr2pPrjcx7nnv993/sc958AhDnGIQxzi/xryoMV/ZnbWjXw/p5MkzkVR7DlOJAD8OIrkRKGgG80mHeT38CCMzExNiVarNd5LknGjdQCIgPi/pomofwjEjlLqzucffbT+p3ffNfv99r4ETB89KldXV0uAGCOi7JPG+5sk0+etgWhjvFC4Vb11K9krhz0LKMSx6nQ6syhEiELsyQYLIWMSpdQ/6q1WZy829pQDcRi63V7vU2If5BnWYzJNEs9z3VaSpnpUGyMLiIPASZJkWkgZ7Yd8hk0RoIzWIgqCVjdJRkrykRicKp+AJEnyKPBAyGdAFIiIuU6nEy4sLIzWd5SXA9d1yZhPC8fxtlcZhk7TaaP1WRTiA0epDwbbyJggTZJ5QFxSrvvH7X0N54PWa0KIWqvXGzqUnFEEcIkUUrj3Im8FXOD8Jq0fISkrKEQ7a2PyRHQGiM6kSVJ1lKoM9mWbhBASkepXqCExUhwgl0sh7smeR5/J29tAaz092E5Ejwxcl+9hm0PJJaJoFE5DCzhVPsHEfcSDi/3tsHmV++qXvzJ0n6HZVJeqEoVQO7VLx6kCwKq9bUspq4PtiPjhwHXlEwayigQQvv/Xvwwd2kOXUQf7lWd8p/i3qABRLKT8nRW0BSFEhYzJc4Ir1/3zLjZQCNHqpWlvGF5DV6HQdadAiMn7CNg3+O9stL7TSdPaMLaGCqEnz57lMhc8UOYWdoD8V4b8H2C5VPwsAPDxpH12EwCuVWrLa+VS8VsAcON2feOfSS85cbSQ/6YU4tztevOFzEA+8OYEYrzW6izu9BF+J1Dqh7fqG89ub/McGQeumsv6833su193pEwB4KfMg5+XS8XTldry+1m/cqk4DgDvcbK8x4QB4O+27QkAmAeA8wDwGgAcT5PU5TmLQPE5IlgaJMDEAKgBABeyZxNRcE5JeTEjzAL5XIzDiwA8c4UZBOAZ7Bw/N0RMfvFILnhGSXmpX2kB/oAAbwLAU+VS8dsA8JNyqXi8Ulu+aT/zGtgQeorJV2rLL7NiK+Q7VuH5+eeev8k/F56xGDIfd9P0rYxoMY4u9T+OODMoSgqcQ4S5yHNn7P0Mv9dN9XVtzGIv1VcRca7R6T1bW994nD1qRV9q9ZIL1ZX1L1X/s/rcQFQ8YSPjGTv6LOg0PxP24ZgNF/bGmFXJ92++9aurq9OThXeOTYy97ggxX293P8rCItH6+nKj+eNU68tMlt3PbYk2i9qYy6HrzFsPTLN47rvSbF+34RI3u70tbypHvtRJ0hf69hFwIo5OWtJghXBSvFguFfn6+zzIMDCVYEVX2BsDLmIPLPx7Zf0HxpgjKMXkdGHsw4G4/TkQLU3lI068OSUl9FL93W7avu468vlsVPlgQdtzJNH6e4M5QkRb7yAgeK56CQBe59hnIZXa8pVyqfgiAPzeRs34oIDzldrytW359QUA+JsxRiJyqN0tn2Oh/yMm30315USbfr2Pffc33VR/zOLQxnZqTJWFcrwDwONZ//HQ/yIXA5t7oA01iGgJ7haFXKr1r2+vNd6ZnSywrZ9lPDl0KrXlG+VS8eVBAaczYwPoKyQAaVe4/Xk6JxoTXGu2v9FNdSN7PXSdq5GnLvVScRUsGQ4Hz5HXpBAXBw07QsxZUX1wKOU891xpLHeRAKpamzdur9d/gZv8tkbORkcWIZyvN9C6CAZLlE0Uzo21fy2vLAopxhDFGP8i4T5gD3jKmclyZRRwHrEYYhjTIGPWiWijq/U9F/+cD7v+VotHjmBjfZ2nEHlEHErAQYA2FWyQMXUA3OgkSbqT2V0JNep1tC5EsCH0MIGbWxy7DvKuAngBZi8fOnnLG8mYfQjwvL47N3+MYMgqIaCHqWlXAbtOp1vtNoS+D1prHxDdzXLa35BKifpngVue3hv64wMDI7JZ8HjQekTUEyi6qTE77uDddz3QS1PtKWWMMc7mliHwPJ2PNpDpGG3cvietCM4+IqNJG01GA98RWZpWLGZbjcyLTA8A28gDg2CsDQJDXSDohFHUaXe7e/NAhl+++mr627ffTskYCSi4IiRAlBBRm4BWeJTIGEHGaEToAEGTiOpExCu0JvL7AMZukKIxhmO7DUQriLgByIt4NID96GTyiTGmGefzjTtrawcXq3EQuL7jxL5yIt9xPF8pefLk3fX5155+GsdzOeVJ6eSjqO+SXBBgoJQKlApyvh9O5PPeG1euwGOPnYHQ84QnpRsolQtcdzzw3ElfOVO+4+RPnSg/mJXTZKGAoefJ0PPw+MlPbC6MDNeR6DnS9ZUT8uAESoWzx4492GXfQcOVDg+KCFxXLLwy2s7cIQ5xiEMcYu8AgP8CMaU4Giz4k+UAAAAASUVORK5CYII=',
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
    result      char(3)     not null,
    description text        not null,
    space       float       not null,
    time        float       not null,
    dialect     varchar(45) not null,
    code_length int         not null,
    submit_time bigint      not null default floor(extract(epoch from now())),
    valid       bool        not null default true
);
create table if not exists "problem"
(
    id                  serial primary key,
    assignment_id       int   not null,
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
    id            serial primary key,
    database_path text not null,
    valid         bool not null default true
);
create table if not exists "judge_point"
(
    id                serial primary key,
    problem_id        int  not null,
    before_sql        text,
    after_sql         text,
    judge_script_id   int  not null,
    answer            text not null,
    judge_database_id int  not null,
    valid             bool not null default true
);
create table if not exists "permission"
(
    id        serial primary key,
    role      varchar not null,
    allowance text    not null,
    valid     bool    not null default true,
    primary key (role, allowance)
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
    id     serial primary key,
    script text not null,
    valid  bool not null default true
);
create table if not exists "assignment"
(
    id                   serial primary key,
    title                text        not null,
    description          text        not null,
    start_time           bigint      not null,
    end_time             bigint      not null,
    status               varchar(45) not null,
    full_score           int         not null,
    sample_database_path text        not null,
    valid                bool        not null default true
);
create table if not exists "code"
(
    id          serial primary key,
    code        text,
    code_length int    not null,
    submit_time bigint not null default floor(extract(epoch from now())),
    valid       bool   not null default true
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
    create_date   bigint not null default floor(extract(epoch from now())),
    last_modified bigint not null default floor(extract(epoch from now())),
    valid         bool   not null default true
);
insert into "user" (username, password, nickname)
values ('11811407@mail.sustech.edu.cn', 'lsl213', 'god1'),
       ('11812318@mail.sustech.edu.cn', 'zjxzjx', 'god2'),
       ('11813207@mail.sustech.edu.cn', 'nqsnqs', 'god3'),
       ('11811620@mail.sustech.edu.cn', 'mzymzy', 'god4');
insert into "tag" (keyword, description)
VALUES ('k1', 'd1'),
       ('k2', 'd2'),
       ('k3', 'd3'),
       ('k4', 'd4');
insert into "problem_tag" (problem_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);
insert into "problem" (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                       number_solve,
                       index_in_assignment, solution)
VALUES (1, 'test1', 'test1description', 100, 45.55, 66.66, 6, 5, 1, 'no solution');
commit;