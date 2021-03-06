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