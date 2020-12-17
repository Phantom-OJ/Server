drop table problem;
create table problem
(
    id                  serial               not null
        constraint problem_pkey
            primary key,
    assignment_id       integer              not null,
    title               text                 not null,
    description         text                 not null,
    full_score          integer              not null,
    space_limit         integer default 256  not null,
    time_limit          integer default 3000 not null,
    number_submit       integer default 0    not null,
    number_solve        integer default 0    not null,
    index_in_assignment integer              not null,
    solution            text                 not null,
    valid               boolean default true not null,
    status              varchar default 'private':: character varying not null,
    type                varchar(45),
    judge_script_id     integer
);

INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (3, 2, 'exercise 1', 'List all films from Portugal or Brazil', 100, 256, 3000, 0, 0, 1, 'asdasdasd', true,
        'public', 'select', 1);
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (6, 2, 'join 1', 'Longest film directed by a woman?', 100, 256, 10000, 0, 0, 1, 'not given', true, 'public',
        'select', null);
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (5, 2, 'exercise 3', 'Spanish films that contain neither a (in any case) nor o (in any case) in their title',
        100, 256, 10000, 0, 0, 3, 'not given', true, 'public', 'select', null);
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (4, 2, 'exercise 2', 'American films that were released the year you were born', 100, 256, 10000, 0, 0, 2,
        'not given', true, 'public', 'select', null);
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (2, 1, 'trigger_test', 'trigger_test_description', 100, 256, 10000, 13, 2, 6, 'no public', true, 'public',
        'trigger', 2);
INSERT INTO public.problem (id, assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                            number_solve, index_in_assignment, solution, valid, status, type, judge_script_id)
VALUES (1, 1, 'select_test', 'select_test_description', 100, 46, 10000, 21, 12, 1, 'no solution', true, 'public',
        'select', 1);