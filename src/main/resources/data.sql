INSERT INTO app_user (id, username, password, role)
VALUES (
           1,
           'nathiya',
           '$2a$10$/gsbrCh/k3YJr.YiK2Dxx.csNrZe.tuCF4lEPQXxM6Tdm2NnR64Xi',
           'USER'
       )

    ON CONFLICT (id) DO NOTHING;


INSERT INTO employee
(emp_id, e_name, age, salary, joining_date, dept, dept_no)
VALUES
    (8, 'Nathiya', 25, 960000, '2026-09-21', 'IT', 2)
    ON CONFLICT (emp_id) DO NOTHING;