begin transaction;

insert into "user" (username, password, nickname,role)
values ('11811407@mail.sustech.edu.cn', '$2a$10$ZkD4PeLG6unGepOIPHONjO7SnISjQ9qBibXqf4uhbQK.7buetZKN6', 'god1','ROLE_TEACHER'),
       ('11813207@mail.sustech.edu.cn', '$2a$10$lbvy1xOwq0zHU.nsoxZmWeoYGmsrCI1545oh3Ahvh0VAnkTR/Nk2q', 'god2','ROLE_TEACHER'),
       ('11812318@mail.sustech.edu.cn', '$2a$10$kP9KNT/Hb.QOrCaEItzMRuKrfhWOOttUt2R/46.XjHdjPX7FGqcjm', 'god3','ROLE_TEACHER'),
       ('11811620@mail.sustech.edu.cn', '$2a$10$Lp2cgwXTnh8jranXt6i/yuTdwb.3t1RkoFpKPByshJ84VgE1Mqg5W', 'god4','ROLE_TEACHER'),
       ('leemdragon233@yahoo.com', '$2a$10$I/1HP/qBQlpI/A.UGEwwA.2NI04JOZ97Wya1HHT4mHA4SiqYK6h2m', 'god5','ROLE_STUDENT'),
       ('leemdragon233@gmail.com', '$2a$10$I/1HP/qBQlpI/A.UGEwwA.2NI04JOZ97Wya1HHT4mHA4SiqYK6h2m', 'god6','ROLE_STUDENT');
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
insert into "announcement" (title, description)
VALUES ('announcement1', 'The built-in CSS will be replaced after update / reinstall, DO NOT MODIFY THEM.
Refer https://support.typora.io/Add-Custom-CSS/ when you want to modify those CSS.
Refer https://support.typora.io/About-Themes/ if you want to create / install new themes. '),
       ('announcement2', 'This is the basic formula for fibonacci $f(n)=f(n-1)+f(n-2)$');
-- 以下是权限表
insert into "permission" (role, allowance)

VALUES ('ROLE_STUDENT', 'basic operations'),
       ('ROLE_SA', 'view all submissions'),
       ('ROLE_SA', 'provide the solution'),
       ('ROLE_SA', 'manage the announcement'),
       ('ROLE_SA', 'view all groups'),
       ('ROLE_SA', 'view all assignments'),
       ('ROLE_SA', 'view all codes'),


       ('ROLE_TEACHER', 'view all submissions'),
       ('ROLE_TEACHER', 'provide the solution'),
       ('ROLE_TEACHER', 'manage the announcement'),
       ('ROLE_TEACHER', 'view all groups'),
       ('ROLE_TEACHER', 'view all assignments'),
       ('ROLE_TEACHER', 'view all codes'),
       ('ROLE_TEACHER', 'create groups'),
       ('ROLE_TEACHER', 'create assignment'),
       ('ROLE_TEACHER', 'modify assignment'),
       ('ROLE_TEACHER', 'view judge details'),
       ('ROLE_TEACHER', 'grant other users'),
       ('ROLE_TEACHER', 'view all permissions')
;
insert into "group" (description)
values ('all_members'),
       ('lab1'),
       ('lab2'),
       ('lab3'),
       ('lab4'),
       ('lab5'),
       ('lab6');
insert into user_group (user_id, group_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (1, 2),
       (1, 3),
       (1, 4);

insert into assignment_group (assignment_id, group_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);

-- SELECT setval('problem_id_seq',0,true) FROM public.problem;
INSERT INTO public.judge_point (problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id,
                                valid, dialect)
VALUES (1, null, null, 1, '"title|country|year_released"
"The Adjuster|ca|1991"
"The Commitments|ie|1991"
"The Miracle|gb|1991"
 ', 1, true, 'pgsql');
INSERT INTO public.judge_point (problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id,
                                valid, dialect)
VALUES (1,
        'insert into movies (movieid, title, country, year_released, runtime) values (9539, ''The Sliver Bullet'', ''us'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9540, ''The Gold Bullet'', ''cn'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9541, ''The Bronze Bullet'', ''cn'', 1992, 999);',
        null, 1, '"title|country|year_released"
"The Adjuster|ca|1991"
"The Commitments|ie|1991"
"The Miracle|gb|1991"
"The Gold Bullet|cn|1991', 1, true, 'pgsql');
INSERT INTO public.judge_point (problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id,
                                valid, dialect)
VALUES (2, null, 'CREATE TRIGGER people_trigger BEFORE INSERT on people FOR EACH ROW EXECUTE PROCEDURE valid_check();
INSERT INTO people VALUES (''130631190002140071'',''张三'');INSERT INTO people VALUES (''130631190002140079'',''张三'');INSERT INTO people VALUES (''44190019971024031X'',''李四'');INSERT INTO people VALUES (''441900199710240311'',''李四'');INSERT INTO people VALUES (''441881202011116667'',''王五'');INSERT INTO people VALUES (''44188120201111666X'',''王五'');INSERT INTO people VALUES (''001122192303127898'',''Lisa'');INSERT INTO people VALUES (''001122192303127892'',''Lisa'');INSERT INTO people VALUES (''110108186701310096'',''乃万'');INSERT INTO people VALUES (''110108186701310090'',''乃万'');INSERT INTO people VALUES (''130209192705264310'',''风清扬'');INSERT INTO people VALUES (''130209192705264314'',''风清扬'');INSERT INTO people VALUES (''140926193309253012'',''慕容复'');INSERT INTO people VALUES (''14092619330925301X'',''慕容复'');INSERT INTO people VALUES (''141022197702022098'',''虚竹'');INSERT INTO people VALUES (''141022197702022091'',''虚竹'');INSERT INTO people VALUES (''150223197604312209'',''张小龙'');INSERT INTO people VALUES (''15022319760431220X'',''张小龙'');INSERT INTO people VALUES (''150223199902292196'',''pony'');INSERT INTO people VALUES (''15022319990229219X'',''pony'');INSERT INTO people VALUES (''150223200002292137'',''martin'');INSERT INTO people VALUES (''15022320000229213X'',''martin'');INSERT INTO people VALUES (''150400202001002228'',''Tom'');INSERT INTO people VALUES (''15040020200100222X'',''Tom'');INSERT INTO people VALUES (''150600201802291118'',''张无忌'');INSERT INTO people VALUES (''15060020180229111X'',''张无忌'');INSERT INTO people VALUES (''210106188802331902'',''赵敏'');INSERT INTO people VALUES (''21010618880233190X'',''赵敏'');INSERT INTO people VALUES (''210106999903040011'',''段誉'');INSERT INTO people VALUES (''21010699990304001X'',''段誉'');INSERT INTO people VALUES (''210505197503210043'',''李秋水'');INSERT INTO people VALUES (''21050519750321004X'',''李秋水'');INSERT INTO people VALUES (''210700197404060026'',''李沧海'');INSERT INTO people VALUES (''21070019740406002X'',''李沧海'');INSERT INTO people VALUES (''210700177707210898'',''逍遥子'');INSERT INTO people VALUES (''210700177707210890'',''逍遥子'');INSERT INTO people VALUES (''210902197107210992'',''无崖子'');INSERT INTO people VALUES (''21090219710721099X'',''无崖子'');INSERT INTO people VALUES (''310117192507121990'',''郭靖'');INSERT INTO people VALUES (''31011719250712199X'',''郭靖'');INSERT INTO people VALUES (''310117202506008888'',''黄蓉'');INSERT INTO people VALUES (''31011720250600888X'',''黄蓉'');INSERT INTO people VALUES (''140106199903310217'',''小龙女'');INSERT INTO people VALUES (''14010619990331021X'',''小龙女'');INSERT INTO people VALUES (''320206199809230041'',''杨过'');INSERT INTO people VALUES (''320206199809230042'',''杨过'');INSERT INTO people VALUES (''320206299907051985'',''杨逍'');INSERT INTO people VALUES (''320206299907051983'',''杨逍'');INSERT INTO people VALUES (''421122321011121976'',''韦一笑'');INSERT INTO people VALUES (''421122321011121978'',''韦一笑'');select id,name,address,birthday from people;',
        2, '"id|name|address|birthday"
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
"421122321011121976|韦一笑|湖北省,黄冈市,红安县|32101112"', 2, true, 'pgsql');
INSERT INTO public.judge_database (database_url, valid, keyword, dialect)
VALUES ('{"host":"localhost","start_port":12000,"end_port":12010,"image_id":12345}
', true, 'k1', 'pgsql');
INSERT INTO public.judge_database (database_url, valid, keyword, dialect)
VALUES ('{"host":"localhost","start_port":12000,"end_port":12010,"image_id":12345}',
        true, 'k2', 'pgsql');
INSERT INTO public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 'select_test', 'select_test_description', 100, 46, 10000, 14, 5, 1, 'no solution', true, 'public',
        'select', 1);
INSERT INTO public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 'trigger_test', 'trigger_test_description', 100, 256, 10000, 13, 2, 6, 'no public', true, 'public',
        'trigger', 2);
INSERT INTO public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 'test5', 'test5', 20, 22, 1, 10, 1, 5, 'store a markdown', true, 'public', 'select', 1);
INSERT INTO public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 'test4', 'test4', 20, 22, 1, 9, 1, 4, 'store a markdown', true, 'public', 'select', 1);
INSERT INTO public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 'test3', 'est3', 20, 33, 1, 8, 2, 3, 'store a markdown', true, 'public', 'select', 1);
INSERT INTO public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 'test2', '# Computer Network hw1

**Name: **黎诗龙

**SID: **11811407

### circuit switch

The total delay is
$$
d_{cs} = \frac{x}{b}+kd+s
$$',
        20, 55, 33, 7, 2, 2, 'store a markdown', true, 'public', 'select', 1);
insert into problem(assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                    number_solve, index_in_assignment, solution, status, type, judge_script_id)
VALUES (2, 'p2.1', 'd2.1', 20, 256, 3000, 0, 0, 1, '### MySQL

```mysql
SELECT * FROM t WHERE id = 1;
```

### PgSQL

```plsql
SELECT * FROM t WHERE id = 1;
```

### SQLite

```sqlite
SELECT * FROM t WHERE id = 1;
```', 'public', 'select', 1),
       (2, 'p2.2', 'd2.2', 20, 256, 3000, 0, 0, 2, '### MySQL

```mysql
SELECT * FROM t WHERE id = 1;
```

### PgSQL

```plsql
SELECT * FROM t WHERE id = 1;
```

### SQLite

```sqlite
SELECT * FROM t WHERE id = 1;
```', 'public', 'select', 1),
       (2, 'p2.3', 'd2.3', 20, 256, 3000, 0, 0, 3,
        '### MySQL
```mysql
SELECT * FROM t WHERE id = 1;
```
### PgSQL
```plsql
SELECT * FROM t WHERE id = 1;
```
### SQLite
```sqlite
SELECT * FROM t WHERE id = 1
```', 'public', 'select', 1),
       (2, 'p2.4', 'd2.4', 20, 256, 3000, 0, 0, 4, '### MySQL

```mysql
SELECT * FROM t WHERE id = 1;
```

### PgSQL

```plsql
SELECT * FROM t WHERE id = 1;
```

### SQLite

```sqlite
SELECT * FROM t WHERE id = 1;
```', 'public', 'select', 1),
       (2, 'p2.5', 'd2.5', 20, 256, 3000, 0, 0, 5, '### MySQL

```mysql
SELECT * FROM t WHERE id = 1;
```

### PgSQL

```plsql
SELECT * FROM t WHERE id = 1;
```

### SQLite

```sqlite
SELECT * FROM t WHERE id = 1;
```', 'public', 'select', 1);

INSERT INTO public.assignment (title, description, start_time, end_time, status, full_score,
                               valid)
VALUES ('title1', '# Computer Network hw1

$$
d_{pw} = d_t+d_{sf}+d_p = \frac{x}{b}+kd+(k-1)\cdot \frac{p}{b}
$$
',
        1605546219888, 1605546251315, 'Running', 100, true);

insert into public.assignment (title, description, start_time, end_time, full_score)
VALUES ('title2', 'description2', 1, 2, 100);



commit;