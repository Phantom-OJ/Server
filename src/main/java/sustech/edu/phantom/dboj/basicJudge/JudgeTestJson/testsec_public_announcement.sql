





INSERT INTO public.judge_database (id, database_url, valid) VALUES (1, 'jdbc:postgresql://10.20.87.51:10002/postgres?allowMultiQueries=true&currentSchema=public&reWriteBatchedInserts=true
', true);
INSERT INTO public.judge_point (id, problem_id, before_sql, after_sql, judge_script_id, answer, judge_database_id, valid) VALUES (1, 1, null, null, 1, '"title|country|year_released",
"The Adjuster|ca|1991",
"The Commitments|ie|1991",
"The Miracle|gb|1991"
 ', 1, true);



