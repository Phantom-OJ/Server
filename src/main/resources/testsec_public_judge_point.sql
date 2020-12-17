create table judge_point
(
    id                serial                not null
        constraint judge_point_pkey
            primary key,
    problem_id        integer               not null,
    before_sql        text,
    after_sql         text,
    answer            text                  not null,
    judge_database_id integer               not null,
    valid             boolean  default true not null,
    dialect           char(10) default 'pgsql'::bpchar not null,
    judge_script_id   integer
);

alter table judge_point
    owner to postgres;

INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (14, 1,
        'insert into movies (movieid, title, country, year_released, runtime) values (9539, ''The Sliver Bullet'', ''us'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9540, ''The Gold Bullet'', ''cn'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9541, ''The Bronze Bullet'', ''cn'', 1992, 999);',
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%''',
        1, true, 'pgsql     ', 1);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (13, 1, null, null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%''',
        1, true, 'pgsql     ', 1);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (16, 6, null, null, 'select distinct
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
 and c.credited_as = ''D'')', 1, true, 'pgsql     ', 1);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (17, 6, null, null, 'select distinct
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
 and c.credited_as = ''D'')', 2, true, 'mysql     ', 1);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (19, 1,
        'insert into movies (movieid, title, country, year_released, runtime) values (9539, ''The Sliver Bullet'', ''us'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9540, ''The Gold Bullet'', ''cn'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9541, ''The Bronze Bullet'', ''cn'', 1992, 999);',
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%''',
        3, true, 'sqlite    ', 1);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (18, 1,
        'insert into movies (movieid, title, country, year_released, runtime) values (9539, ''The Sliver Bullet'', ''us'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9540, ''The Gold Bullet'', ''cn'', 1991, 999);insert into movies (movieid, title, country, year_released, runtime) values (9541, ''The Bronze Bullet'', ''cn'', 1992, 999);',
        null,
        'SELECT title, country, year_released FROM movies WHERE country <> ''us'' AND year_released = 1991 AND title LIKE ''The%''',
        2, true, 'mysql     ', 1);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, answer, judge_database_id, valid, dialect,
                                judge_script_id)
VALUES (15, 2, null, 'CREATE TRIGGER people_trigger BEFORE INSERT on people FOR EACH ROW EXECUTE PROCEDURE valid_check();
INSERT INTO people VALUES (''130631190002140071'',''张三'');INSERT INTO people VALUES (''130631190002140079'',''张三'');INSERT INTO people VALUES (''44190019971024031X'',''李四'');INSERT INTO people VALUES (''441900199710240311'',''李四'');INSERT INTO people VALUES (''441881202011116667'',''王五'');INSERT INTO people VALUES (''44188120201111666X'',''王五'');INSERT INTO people VALUES (''001122192303127898'',''Lisa'');INSERT INTO people VALUES (''001122192303127892'',''Lisa'');INSERT INTO people VALUES (''110108186701310096'',''乃万'');INSERT INTO people VALUES (''110108186701310090'',''乃万'');INSERT INTO people VALUES (''130209192705264310'',''风清扬'');INSERT INTO people VALUES (''130209192705264314'',''风清扬'');INSERT INTO people VALUES (''140926193309253012'',''慕容复'');INSERT INTO people VALUES (''14092619330925301X'',''慕容复'');INSERT INTO people VALUES (''141022197702022098'',''虚竹'');INSERT INTO people VALUES (''141022197702022091'',''虚竹'');INSERT INTO people VALUES (''150223197604312209'',''张小龙'');INSERT INTO people VALUES (''15022319760431220X'',''张小龙'');INSERT INTO people VALUES (''150223199902292196'',''pony'');INSERT INTO people VALUES (''15022319990229219X'',''pony'');INSERT INTO people VALUES (''150223200002292137'',''martin'');INSERT INTO people VALUES (''15022320000229213X'',''martin'');INSERT INTO people VALUES (''150400202001002228'',''Tom'');INSERT INTO people VALUES (''15040020200100222X'',''Tom'');INSERT INTO people VALUES (''150600201802291118'',''张无忌'');INSERT INTO people VALUES (''15060020180229111X'',''张无忌'');INSERT INTO people VALUES (''210106188802331902'',''赵敏'');INSERT INTO people VALUES (''21010618880233190X'',''赵敏'');INSERT INTO people VALUES (''210106999903040011'',''段誉'');INSERT INTO people VALUES (''21010699990304001X'',''段誉'');INSERT INTO people VALUES (''210505197503210043'',''李秋水'');INSERT INTO people VALUES (''21050519750321004X'',''李秋水'');INSERT INTO people VALUES (''210700197404060026'',''李沧海'');INSERT INTO people VALUES (''21070019740406002X'',''李沧海'');INSERT INTO people VALUES (''210700177707210898'',''逍遥子'');INSERT INTO people VALUES (''210700177707210890'',''逍遥子'');INSERT INTO people VALUES (''210902197107210992'',''无崖子'');INSERT INTO people VALUES (''21090219710721099X'',''无崖子'');INSERT INTO people VALUES (''310117192507121990'',''郭靖'');INSERT INTO people VALUES (''31011719250712199X'',''郭靖'');INSERT INTO people VALUES (''310117202506008888'',''黄蓉'');INSERT INTO people VALUES (''31011720250600888X'',''黄蓉'');INSERT INTO people VALUES (''140106199903310217'',''小龙女'');INSERT INTO people VALUES (''14010619990331021X'',''小龙女'');INSERT INTO people VALUES (''320206199809230041'',''杨过'');INSERT INTO people VALUES (''320206199809230042'',''杨过'');INSERT INTO people VALUES (''320206299907051985'',''杨逍'');INSERT INTO people VALUES (''320206299907051983'',''杨逍'');INSERT INTO people VALUES (''421122321011121976'',''韦一笑'');INSERT INTO people VALUES (''421122321011121978'',''韦一笑'');select id,name,address,birthday from people;', '["130631190002140071|张三|河北省,保定市,望都县|19000214",
"44190019971024031X|李四|广东省,东莞市|19971024",
"441881202011116667|王五|广东省,清远市,英德市|20201111",
"130209192705264310|风清扬|河北省,唐山市,曹妃甸区|19270526",
"140926193309253012|慕容复|山西省,忻州市,静乐县|19330925",
"141022197702022098|虚竹|山西省,临汾市,翼城县|19770202",
"150223200002292137|martin|内蒙古自治区,包头市,达尔罕茂明安联合旗|20000229",
"210106999903040011|段誉|辽宁省,沈阳市,铁西区|99990304",
"210505197503210043|李秋水|辽宁省,本溪市,南芬区|19750321",
"210700197404060026|李沧海|辽宁省,锦州市|19740406",
"210902197107210992|无崖子|辽宁省,阜新市,海州区|19710721",
"310117192507121990|郭靖|上海市,松江区|19250712",
"140106199903310217|小龙女|山西省,太原市,迎泽区|19990331",
"320206199809230041|杨过|江苏省,无锡市,惠山区|19980923",
"320206299907051985|杨逍|江苏省,无锡市,惠山区|29990705",
"421122321011121976|韦一笑|湖北省,黄冈市,红安县|32101112"]', 2, true, 'pgsql', 2);