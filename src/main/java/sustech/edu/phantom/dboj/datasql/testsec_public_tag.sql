create table tag
(
    id          serial               not null
        constraint tag_pkey
            primary key,
    keyword     text                 not null,
    description text,
    valid       boolean default true not null
);

INSERT INTO public.tag (id, keyword, description, valid) VALUES (17, 'k1', 'd1', true);
INSERT INTO public.tag (id, keyword, description, valid) VALUES (18, 'k2', 'd2', true);
INSERT INTO public.tag (id, keyword, description, valid) VALUES (19, 'k3', 'd3', true);
INSERT INTO public.tag (id, keyword, description, valid) VALUES (20, 'k4', 'd4', true);