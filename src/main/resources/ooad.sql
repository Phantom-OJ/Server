begin transaction;
drop table if exists "announcement", assignment, assignment_group,
    code, grade, "group", judge_database, judge_point,
    judge_script, "permission", problem, problem_tag,
    record, tag, "user", user_group;
create table if not exists "user"
(
    id         serial primary key,
    username   varchar(45)  not null unique,
    password   varchar(100) not null,
    nickname   varchar(45),
    role       varchar      not null default 'ROLE_STUDENT',
    avatar     text         not null default 'data:image/png;base64,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',
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
    space       int         not null,
    time        int         not null,
    dialect     varchar(45) not null,
    code_length int         not null,
    submit_time bigint      not null default floor(extract(epoch from now())),
    valid       bool        not null default true
);
create table if not exists "record_problem_judge_point"
(
    id                serial primary key,
    record_id         int     not null,
    problem_id        int     not null,
    judge_point_index int     not null,
    time              bigint  not null,
    space             bigint  not null,
    result            char(3) not null,
    description       text,

    unique (problem_id, judge_point_index)
);
create table problem
(
    id serial not null
        constraint problem_pkey
            primary key,
    assignment_id integer not null,
    title text not null,
    description text not null,
    full_score integer not null,
    space_limit integer not null,
    time_limit integer not null,
    number_submit integer not null,
    number_solve integer not null,
    index_in_assignment integer not null,
    solution text not null,
    valid boolean default true not null,
    status varchar default 'private'::character varying not null,
    type varchar(45)
);

create table if not exists "judge_database"
(
    id           serial primary key,
    database_url text not null default 'jdbc:postgresql://localhost:5432/postgres',
    valid        bool not null default true
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
    status               varchar(45) not null default 'private',
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

--这里要改的，现在只是测试用的，密码都没有加密
insert into "user" (username, password, nickname)
values ('11811407@mail.sustech.edu.cn', '$2a$10$ZkD4PeLG6unGepOIPHONjO7SnISjQ9qBibXqf4uhbQK.7buetZKN6', 'god1'),
       ('11813207@mail.sustech.edu.cn', '$2a$10$lbvy1xOwq0zHU.nsoxZmWeoYGmsrCI1545oh3Ahvh0VAnkTR/Nk2q', 'god1'),
       ('11812318@mail.sustech.edu.cn', '$2a$10$kP9KNT/Hb.QOrCaEItzMRuKrfhWOOttUt2R/46.XjHdjPX7FGqcjm', 'god1'),
       ('11811620@mail.sustech.edu.cn', '$2a$10$Lp2cgwXTnh8jranXt6i/yuTdwb.3t1RkoFpKPByshJ84VgE1Mqg5W', 'god1')
;
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
insert into "announcement" (title, description)
VALUES ('announcement1', 'The built-in CSS will be replaced after update / reinstall, DO NOT MODIFY THEM.
Refer https://support.typora.io/Add-Custom-CSS/ when you want to modify those CSS.
Refer https://support.typora.io/About-Themes/ if you want to create / install new themes. '),
       ('announcement2', 'This is the basic formula for fibonacci $f(n)=f(n-1)+f(n-2)$');
-- 以下是权限表
insert into "permission" (role, allowance)
VALUES ('ROLE_STUDENT', 'modify personal information'),
       ('ROLE_STUDENT', 'check points'),
       ('ROLE_STUDENT', 'submit codes'),
       ('ROLE_STUDENT', 'review codes'),
       ('ROLE_STUDENT', 'view results'),
       ('ROLE_STUDENT', 'view ranking'),

       ('ROLE_SA', 'modify personal information'),
       ('ROLE_SA', 'check points'),
       ('ROLE_SA', 'submit codes'),
       ('ROLE_SA', 'review codes'),
       ('ROLE_SA', 'view results'),
       ('ROLE_SA', 'view ranking'),
       ('ROLE_SA', 'view all submissions'),
       ('ROLE_SA', 'provide the solution'),

       ('ROLE_TEACHER', 'modify personal information'),
       ('ROLE_TEACHER', 'check points'),
       ('ROLE_TEACHER', 'submit codes'),
       ('ROLE_TEACHER', 'review codes'),
       ('ROLE_TEACHER', 'view results'),
       ('ROLE_TEACHER', 'view ranking'),
       ('ROLE_TEACHER', 'view all submissions'),
       ('ROLE_TEACHER', 'provide the solution'),
       ('ROLE_TEACHER', 'create assignment'),
       ('ROLE_TEACHER', 'provide sample database'),
       ('ROLE_TEACHER', 'create problem'),
       ('ROLE_TEACHER', 'provide description'),
       ('ROLE_TEACHER', 'provide space and time limit'),
       ('ROLE_TEACHER', 'provide sample output'),
       ('ROLE_TEACHER', 'modify assignment'),
       ('ROLE_TEACHER', 'grant other users');
insert into "group" (description)
values ('group1'),
       ('group2'),
       ('group3'),
       ('group4'),
       ('group5'),
       ('group6');
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id, valid) VALUES (1, 1, null, null, 1, '"title|country|year_released"
"The Adjuster|ca|1991"
"The Commitments|ie|1991"
"The Miracle|gb|1991"
 ', 1, true);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id, valid) VALUES (2, 1, 'insert into movies (movieid, title, country, year_released, runtime) values (9539, ''The Sliver Bullet'', ''us'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9540, ''The Gold Bullet'', ''cn'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9541, ''The Bronze Bullet'', ''cn'', 1992, 999);', null, 1, '"title|country|year_released"
"The Adjuster|ca|1991"
"The Commitments|ie|1991"
"The Miracle|gb|1991"
"The Gold Bullet|cn|1991', 1, true);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id, valid) VALUES (3, 2, null, 'CREATE TRIGGER people_trigger BEFORE INSERT on people FOR EACH ROW EXECUTE PROCEDURE valid_check();
INSERT INTO people VALUES (''130631190002140071'',''张三'');INSERT INTO people VALUES (''130631190002140079'',''张三'');INSERT INTO people VALUES (''44190019971024031X'',''李四'');INSERT INTO people VALUES (''441900199710240311'',''李四'');INSERT INTO people VALUES (''441881202011116667'',''王五'');INSERT INTO people VALUES (''44188120201111666X'',''王五'');INSERT INTO people VALUES (''001122192303127898'',''Lisa'');INSERT INTO people VALUES (''001122192303127892'',''Lisa'');INSERT INTO people VALUES (''110108186701310096'',''乃万'');INSERT INTO people VALUES (''110108186701310090'',''乃万'');INSERT INTO people VALUES (''130209192705264310'',''风清扬'');INSERT INTO people VALUES (''130209192705264314'',''风清扬'');INSERT INTO people VALUES (''140926193309253012'',''慕容复'');INSERT INTO people VALUES (''14092619330925301X'',''慕容复'');INSERT INTO people VALUES (''141022197702022098'',''虚竹'');INSERT INTO people VALUES (''141022197702022091'',''虚竹'');INSERT INTO people VALUES (''150223197604312209'',''张小龙'');INSERT INTO people VALUES (''15022319760431220X'',''张小龙'');INSERT INTO people VALUES (''150223199902292196'',''pony'');INSERT INTO people VALUES (''15022319990229219X'',''pony'');INSERT INTO people VALUES (''150223200002292137'',''martin'');INSERT INTO people VALUES (''15022320000229213X'',''martin'');INSERT INTO people VALUES (''150400202001002228'',''Tom'');INSERT INTO people VALUES (''15040020200100222X'',''Tom'');INSERT INTO people VALUES (''150600201802291118'',''张无忌'');INSERT INTO people VALUES (''15060020180229111X'',''张无忌'');INSERT INTO people VALUES (''210106188802331902'',''赵敏'');INSERT INTO people VALUES (''21010618880233190X'',''赵敏'');INSERT INTO people VALUES (''210106999903040011'',''段誉'');INSERT INTO people VALUES (''21010699990304001X'',''段誉'');INSERT INTO people VALUES (''210505197503210043'',''李秋水'');INSERT INTO people VALUES (''21050519750321004X'',''李秋水'');INSERT INTO people VALUES (''210700197404060026'',''李沧海'');INSERT INTO people VALUES (''21070019740406002X'',''李沧海'');INSERT INTO people VALUES (''210700177707210898'',''逍遥子'');INSERT INTO people VALUES (''210700177707210890'',''逍遥子'');INSERT INTO people VALUES (''210902197107210992'',''无崖子'');INSERT INTO people VALUES (''21090219710721099X'',''无崖子'');INSERT INTO people VALUES (''310117192507121990'',''郭靖'');INSERT INTO people VALUES (''31011719250712199X'',''郭靖'');INSERT INTO people VALUES (''310117202506008888'',''黄蓉'');INSERT INTO people VALUES (''31011720250600888X'',''黄蓉'');INSERT INTO people VALUES (''140106199903310217'',''小龙女'');INSERT INTO people VALUES (''14010619990331021X'',''小龙女'');INSERT INTO people VALUES (''320206199809230041'',''杨过'');INSERT INTO people VALUES (''320206199809230042'',''杨过'');INSERT INTO people VALUES (''320206299907051985'',''杨逍'');INSERT INTO people VALUES (''320206299907051983'',''杨逍'');INSERT INTO people VALUES (''421122321011121976'',''韦一笑'');INSERT INTO people VALUES (''421122321011121978'',''韦一笑'');select id,name,address,birthday from people;', 2, '"id|name|address|birthday"
"130631190002140071|张三|河北省,保定市,望都县|19000214"
"44190019971024031X|李四|广东省,东莞市|19971024"
"441881202011116667|王五|广东省,清远市,英德市|20201111"
"130209192705264310|风清扬|河北省,唐山市,曹妃甸区|19270526"
"140926193309253012|慕容复|山西省,忻州市,静乐县|19330925"
"141022197702022098|虚竹|山西省,临汾市,翼城县|19770202"
"150223200002292137|martin|内蒙古自治区,包头市,达尔罕茂明安联合旗|20000229"
"210106999903040011|段誉|辽宁省,沈阳市,铁西区|99990304"
"210505197503210043|李秋水|辽宁省,本溪市,南芬区|19750321"
"210700197404060026|李沧海|辽宁省,锦州市|19740406"
"210902197107210992|无崖子|辽宁省,阜新市,海州区|19710721"
"310117192507121990|郭靖|上海市,松江区|19250712"
"140106199903310217|小龙女|山西省,太原市,迎泽区|19990331"
"320206199809230041|杨过|江苏省,无锡市,惠山区|19980923"
"320206299907051985|杨逍|江苏省,无锡市,惠山区|29990705"
"421122321011121976|韦一笑|湖北省,黄冈市,红安县|32101112"', 2, true);
INSERT INTO public.judge_database (id, database_url, valid) VALUES (1, 'jdbc:postgresql://47.102.221.90:12002/postgres?allowMultiQueries=true&currentSchema=public&reWriteBatchedInserts=true
', true);
INSERT INTO public.judge_database (id, database_url, valid) VALUES (2, 'jdbc:postgresql://47.102.221.90:12002/postgres?allowMultiQueries=true&currentSchema=trigger_db&reWriteBatchedInserts=true', true);
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit, number_solve, index_in_assignment, solution, valid, status, type) VALUES (1, 1, 'select_test', 'select_test_description', 100, 46, 10000, 14, 5, 1, 'no solution', true, 'public', 'select');
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit, number_solve, index_in_assignment, solution, valid, status, type) VALUES (2, 1, 'trigger_test', 'trigger_test_description', 100, 256, 10000, 13, 2, 6, 'no public', true, 'public', 'trigger');
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit, number_solve, index_in_assignment, solution, valid, status, type) VALUES (7, 1, 'test5', 'test5', 20, 22, 1, 10, 1, 5, 'store a markdown', true, 'public', 'select');
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit, number_solve, index_in_assignment, solution, valid, status, type) VALUES (6, 1, 'test4', 'test4', 20, 22, 1, 9, 1, 4, 'store a markdown', true, 'public', 'select');
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit, number_solve, index_in_assignment, solution, valid, status, type) VALUES (5, 1, 'test3', 'est3', 20, 33, 1, 8, 2, 3, 'store a markdown', true, 'public', 'select');
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit, number_solve, index_in_assignment, solution, valid, status, type) VALUES (4, 1, 'test2', '# Computer Network hw1

**Name: **黎诗龙

**SID: **11811407

## 1

### packet switch

The transmission delay
$$
d_t =\frac{x}{p}\cdot \frac{p}{b} = \frac{x}{b}
$$
The store and forward delay
$$
d_{sf} = (k-1)\cdot \frac{p}{b}
$$
The propagation delay
$$
d_p = kd
$$
The total delay is
$$
d_{pw} = d_t+d_{sf}+d_p = \frac{x}{b}+kd+(k-1)\cdot \frac{p}{b}
$$

### circuit switch

The transmission delay
$$
d_t =\frac{x}{b}
$$
The propagation delay
$$
d_p = kd
$$
The setup delay is $s$.

The total delay is
$$
d_{cs} = \frac{x}{b}+kd+s
$$

If packet switch is smaller:
$$
d_p<d_c\\ \text{i.e.}
\\
\frac{x}{b}+kd+(k-1)\cdot \frac{p}{b}<\frac{x}{b}+kd+s
\\\implies s>\frac{p(k-1)}{b}
$$

## 2

Since in the description it says that *The handshaking process costs 2RTT before transmitting the file.*, we don''t consider transmitting file during the handshaking.

In my solution, I consider `Mb` as `10**6 bits`.

And in my solution, `0.5RTT` means in the final transmission, I just send the file, regardless of the return message from the receiver.  ', 20, 55, 33, 7, 2, 2, 'store a markdown', true, 'public', 'select');
commit;