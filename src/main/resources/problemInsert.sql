SELECT setval('problem_id_seq',6,true) FROM public.problem;

insert into problem(id,assignment_id, title, description, full_score, space_limit, time_limit, index_in_assignment,
                    solution, type)
values (6,2, 'join 1', 'Longest film directed by a woman?', 100,
        256, 10000, 1, 'not given', 'select');

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id,
                                valid, dialect)
VALUES (6, null, null, 1, 'select distinct
 m.title,
 m.country,
 m.year_released,
 m.runtime
from movies m
 join credits c
 on c.movieid = m.movieid
 join people p
 on p.peopleid = c.peopleid
where p.gender = ''F''
 and c.credited_as = ''D''
 and m.runtime =
 (select max(m.runtime) -- NULLs will be ignored
 from movies m
 join credits c
 on c.movieid = m.movieid
 join people p
 on p.peopleid = c.peopleid
 where p.gender = ''F''
 and c.credited_as = ''D'')', 1, true, 'pgsql');

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id,
                                valid, dialect)
VALUES (6, null, null, 1, 'select distinct
 m.title,
 m.country,
 m.year_released,
 m.runtime
from movies m
 join credits c
 on c.movieid = m.movieid
 join people p
 on p.peopleid = c.peopleid
where p.gender = ''F''
 and c.credited_as = ''D''
 and m.runtime =
 (select max(m.runtime) -- NULLs will be ignored
 from movies m
 join credits c
 on c.movieid = m.movieid
 join people p
 on p.peopleid = c.peopleid
 where p.gender = ''F''
 and c.credited_as = ''D'')', 2, true, 'mysql');