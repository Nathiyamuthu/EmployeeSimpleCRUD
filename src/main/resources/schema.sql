CREATE TABLE IF NOT EXISTS employee (
                                        emp_id BIGSERIAL PRIMARY KEY,
                                        e_name VARCHAR(255),
    age INTEGER,
    salary DOUBLE PRECISION,
    joining_date DATE,
    dept VARCHAR(255),
    dept_no BIGINT
    );

CREATE TABLE IF NOT EXISTS app_user (
                                        id BIGSERIAL PRIMARY KEY,
                                        username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
    );