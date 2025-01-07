DROP TABLE IF EXISTS attendance; 
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS app_users;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS leave_request;

CREATE TABLE department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    department_id BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    date_hired TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE app_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    failed_attempt INT,
    account_non_locked BOOLEAN DEFAULT TRUE,
    lock_time TIMESTAMP,
    last_login TIMESTAMP,
    password_reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP,
    role VARCHAR(50) NOT NULL,
    employee_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    date DATE NOT NULL,
    time_in TIMESTAMP,
    time_out TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    UNIQUE (employee_id, date)
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    date DATE NOT NULL,
    shift_start_time TIME NOT NULL,
    shift_end_time TIME NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE leave_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    applied_date TIMESTAMP,
    approved_by BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    FOREIGN KEY (approved_by) REFERENCES employee(id)
);

CREATE TABLE IF NOT EXISTS user_permission (
    user_id BIGINT NOT NULL,
    permission VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES app_users(id)
);

ALTER TABLE app_users ALTER COLUMN failed_attempt INTEGER;