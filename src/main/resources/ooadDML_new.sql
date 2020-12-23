begin transaction;

insert into "user" (username, password, nickname,role)
values ('11811407@mail.sustech.edu.cn', '$2a$10$ZkD4PeLG6unGepOIPHONjO7SnISjQ9qBibXqf4uhbQK.7buetZKN6', 'god1','ROLE_TEACHER'),
       ('11813207@mail.sustech.edu.cn', '$2a$10$lbvy1xOwq0zHU.nsoxZmWeoYGmsrCI1545oh3Ahvh0VAnkTR/Nk2q', 'god2','ROLE_TEACHER'),
       ('11812318@mail.sustech.edu.cn', '$2a$10$kP9KNT/Hb.QOrCaEItzMRuKrfhWOOttUt2R/46.XjHdjPX7FGqcjm', 'god3','ROLE_TEACHER'),
       ('11811620@mail.sustech.edu.cn', '$2a$10$Lp2cgwXTnh8jranXt6i/yuTdwb.3t1RkoFpKPByshJ84VgE1Mqg5W', 'god4','ROLE_TEACHER'),
       ('leemdragon233@yahoo.com', '$2a$10$I/1HP/qBQlpI/A.UGEwwA.2NI04JOZ97Wya1HHT4mHA4SiqYK6h2m', 'god5','ROLE_STUDENT'),
       ('leemdragon233@gmail.com', '$2a$10$I/1HP/qBQlpI/A.UGEwwA.2NI04JOZ97Wya1HHT4mHA4SiqYK6h2m', 'god6','ROLE_STUDENT');
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

insert into public.assignment (id,title, description, start_time, end_time, status, full_score)
values (1,
        'Assignment1: Simple Table Query',
        '### Rules:

All the questions and the sample result sets in this assignment are based on **filmdb.sqlite** we have been uploaded in bb website.

- Your result set, especially the data type and the order of each column, must strictly follow the description and the sample result set in each question.
- You need submit “.sql” files for these six questions. The name of each “.sql” file should be q1, q2, q3, q4, q5, q6 respectively to represent these six questions.
- Do not forget to add ‘;’ in the end of each query.
- Do not compress them into a folder, please submit them directly. Please submit those queries into **sakai website** as soon as possible, so that you can get chance to receive feedback before deadline. <span style="color:red">After the deadline, we will check the assignment automatically by a script and then given your grade, at that time, any argument about your grade of this assignment will not be accepted.</span>',
        1606798800000,
        1608440400000,
        'public',
        100
       );
insert into public.assignment_group (assignment_id, group_id) values (1, 1);
insert into public.assignment_group (assignment_id, group_id) values (1, 2);
insert into public.assignment_group (assignment_id, group_id) values (1, 3);
insert into public.assignment_group (assignment_id, group_id) values (1, 4);


INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (1,
        null,
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';',
        1, true, 'pgsql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (1,
        null,
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';',
        2, true, 'mysql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (1,
        null,
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';',
        3, true, 'sqlite', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (2,
        null,
        null,
        'select count(*) cnt
from (
 select count(movieid) c from credits
 where credited_as = ''A''
 group by peopleid
 )
where c > 30;',
        1, true, 'pgsql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (2,
        null,
        null,
        'select count(*) cnt
from (
 select count(movieid) c from credits
 where credited_as = ''A''
 group by peopleid
 )
where c > 30;',
        2, true, 'mysql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (2,
        null,
        null,
        'select count(*) cnt
from (
 select count(movieid) c from credits
 where credited_as = ''A''
 group by peopleid
 )
where c > 30;',
        3, true, 'sqlite', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (3,
        null,
        null,
        'select round((100.0 * sum(case country when ''us'' then 1 else 0 end)/count(*)), 2) us_percent
from movies
where year_released >= 1970
and year_released < 1980;',
        1, true, 'pgsql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (3,
        null,
        null,
        'select round((100.0 * sum(case country when ''us'' then 1 else 0 end)/count(*)), 2) us_percent
from movies
where year_released >= 1970
and year_released < 1980;',
        2, true, 'mysql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (3,
        null,
        null,
        'select round((100.0 * sum(case country when ''us'' then 1 else 0 end)/count(*)), 2) us_percent
from movies
where year_released >= 1970
and year_released < 1980;',
        3, true, 'sqlite', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (4,
        null,
        null,
        'select case nation
 when ''kr'' then (surname||'' ''||coalesce(first_name, '' ''))
 when ''hk'' then (surname||'' ''||coalesce(first_name, '' ''))
 else (coalesce(first_name, '' '')||'' ''||surname)
 end director
from (select distinct c.peopleid, m.country nation from credits c
join movies m on c.movieid = m.movieid
where m.country in (''kr'', ''hk'', ''gb'', ''ph'')
and m.year_released = 2016
and c.credited_as = ''D'') x
join people p on p.peopleid = x.peopleid
order by director;',
        1, true, 'pgsql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (4,
        null,
        null,
        'select case nation
 when ''kr'' then (surname||'' ''||coalesce(first_name, '' ''))
 when ''hk'' then (surname||'' ''||coalesce(first_name, '' ''))
 else (coalesce(first_name, '' '')||'' ''||surname)
 end director
from (select distinct c.peopleid, m.country nation from credits c
join movies m on c.movieid = m.movieid
where m.country in (''kr'', ''hk'', ''gb'', ''ph'')
and m.year_released = 2016
and c.credited_as = ''D'') x
join people p on p.peopleid = x.peopleid
order by director;',
        2, true, 'mysql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (4,
        null,
        null,
        'select case nation
 when ''kr'' then (surname||'' ''||coalesce(first_name, '' ''))
 when ''hk'' then (surname||'' ''||coalesce(first_name, '' ''))
 else (coalesce(first_name, '' '')||'' ''||surname)
 end director
from (select distinct c.peopleid, m.country nation from credits c
join movies m on c.movieid = m.movieid
where m.country in (''kr'', ''hk'', ''gb'', ''ph'')
and m.year_released = 2016
and c.credited_as = ''D'') x
join people p on p.peopleid = x.peopleid
order by director;',
        3, true, 'sqlite', 1);

insert into public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, status, type, judge_script_id)
values (1,
        'problem1',
        'List the non-US movies released in 1991 and with titles begin with "The".',
        20,
        128,
        3000,
        0,
        0,
        1,
        '### MySQL:

```mysql
SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';
```

### PostgreSQL:

```sql
SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';
```

### SQLite:

```sqlite
SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';
```',
        'public',
        'select',
        null);

insert into public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, status, type, judge_script_id)
values (1,
        'problem2',
        'How many actors have acted more than 30 movies.',
        20,
        128,
        3000,
        0,
        0,
        2,
        '### SQLite:

```sqlite
select count(*) cnt
from (
 select count(movieid) c from credits
 where credited_as = ''A''
 group by peopleid
 )
where c > 30;
```',
        'public',
        'select',
        null);

insert into public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, status, type, judge_script_id)
values (1,
        'problem3',
        'What is the percentage of American films in all films in the 1970s. (The result should be
expressed percentage and approximated to 2 decimal places)',
        20,
        128,
        3000,
        0,
        0,
        3,
        '### SQLite:

```sqlite
select round((100.0 * sum(case country when ''us'' then 1 else 0 end)/count(*)), 2) us_percent
from movies
where year_released >= 1970
and year_released < 1980;
```',
        'public',
        'select',
        null);

insert into public.problem (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, status, type, judge_script_id)
values (1,
        'problem4',
        'List the names of the known directors of 2016 films by ascending order (no need to display anything about the film). The film only from following regions: kr, hk, gb, ph. If the film is Korean(kr) or HONG KONG(hk), the name should be displayed as surname followed by first name, otherwise it must be first name followed by surname.

> hint: Note that coalesce() is required in this version, otherwise all the directors who are only known by one name have a null row returned for them. use coalesce(first_name, '' '') and don''t forget to place a single space between surname and first name.',
        40,
        128,
        4000,
        0,
        0,
        4,
        '### SQLite:

```sqlite
select case nation
 when ''kr'' then (surname||'' ''||coalesce(first_name, '' ''))
 when ''hk'' then (surname||'' ''||coalesce(first_name, '' ''))
 else (coalesce(first_name, '' '')||'' ''||surname)
 end director
from (select distinct c.peopleid, m.country nation from credits c
join movies m on c.movieid = m.movieid
where m.country in (''kr'', ''hk'', ''gb'', ''ph'')
and m.year_released = 2016
and c.credited_as = ''D'') x
join people p on p.peopleid = x.peopleid
order by director;
```',
        'public',
        'select',
        null);

insert into tag (keyword, description) VALUES ('1TQ', 'Single Table Query');
insert into tag (keyword, description) VALUES ('xTQ', 'Multiple Table Query');
insert into tag (keyword, description) VALUES ('easy', 'Simple things should also be taken seriously.');
insert into tag (keyword, description) VALUES ('medium', 'Nothing seek, nothing find.');
insert into tag (keyword, description) VALUES ('hard', 'Difficult circumstances serve as a textbook of life for people.');

INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (1, 'k1', '{"host":"localhost","database":"postgres","image_id":"judgedb:2.0"}
', true, 'pgsql');
INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (4, 'k2', '{"host":"localhost","database":"trigger_db","image_id":"judgedb:2.0"}', true, 'pgsql');
INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (2, 'mysql', '{"host":"localhost","database":"filmdb","image_id":"judge-mysql:1.0"}', true, 'mysql');
INSERT INTO public.judge_database (id, keyword, database_url, valid, dialect)
VALUES (3, 'sqlite', '{"filepath":"resources//filmdb.sqlite"}', true, 'sqlite');
commit;