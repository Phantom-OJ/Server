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
