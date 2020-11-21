insert into "user" (username, password, nickname)
values ('11811407@mail.sustech.edu.cn', 'lsllsl', 'god1'),
       ('11812318@mail.sustech.edu.cn', 'zjxzjx', 'god2'),
       ('11813207@mail.sustech.edu.cn', 'nqsnqs', 'god3'),
       ('11811620@mail.sustech.edu.cn', 'mzymzy', 'god4');
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

insert into "problem" (assignment_id, title, description, full_score, space_limit, time_limit, number_submit,
                       number_solve,
                       index_in_assignment, solution, status)
VALUES (1, 'test1', 'test1description', 100, 45.55, 66.66, 6, 5, 1, 'no solution', 'public'),
       (1, 'zbdbqblmsxq', '### wcsl', 40, 64, 128, 0, 0, 2, 'DROP DATABASE c;', 'public');