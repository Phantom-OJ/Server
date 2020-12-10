insert into public.assignment (title, description, start_time, end_time, status, full_score, sample_database_path)
    values (
        'Assignment1: Simple Table Query',
        '### Rules:

All the questions and the sample result sets in this assignment are based on **filmdb.sqlite** we have been uploaded in bb website.

- Your result set, especially the data type and the order of each column, must strictly follow the description and the sample result set in each question.
- You need submit “.sql” files for these six questions. The name of each “.sql” file should be q1, q2, q3, q4, q5, q6 respectively to represent these six questions.
- Do not forget to add ‘;’ in the end of each query.
- Do not compress them into a folder, please submit them directly. Please submit those queries into **sakai website** as soon as possible, so that you can get chance to receive feedback before deadline. <span style="color:red">After the deadline, we will check the assignment automatically by a script and then given your grade, at that time, any argument about your grade of this assignment will not be accepted.</span>',
        1606798800,
        1608440400,
        'public',
        100,
        ''
    );
insert into public.assignment_group (assignment_id, group_id) values (3, 1);
insert into public.assignment_group (assignment_id, group_id) values (3, 2);
insert into public.assignment_group (assignment_id, group_id) values (3, 3);
insert into public.assignment_group (assignment_id, group_id) values (3, 4);
