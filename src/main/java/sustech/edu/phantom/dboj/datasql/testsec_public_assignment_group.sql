create table assignment_group
(
    assignment_id integer              not null,
    group_id      integer              not null,
    valid         boolean default true not null,
    constraint assignment_group_pkey
        primary key (assignment_id, group_id)
);


INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (1, 1, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (1, 2, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (1, 3, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (1, 4, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (3, 1, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (3, 2, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (3, 3, true);
INSERT INTO public.assignment_group (assignment_id, group_id, valid) VALUES (3, 4, true);