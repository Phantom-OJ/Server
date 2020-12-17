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
        1, true, 'mysql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (1,
        null,
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%'';',
        1, true, 'sqlite', 1);

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
        1, true, 'mysql', 1);

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
        1, true, 'sqlite', 1);

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
        1, true, 'mysql', 1);

INSERT INTO public.judge_point (problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (3,
        null,
        null,
        'select round((100.0 * sum(case country when ''us'' then 1 else 0 end)/count(*)), 2) us_percent
from movies
where year_released >= 1970
and year_released < 1980;',
        1, true, 'sqlite', 1);

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
        1, true, 'mysql', 1);

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
        1, true, 'sqlite', 1);