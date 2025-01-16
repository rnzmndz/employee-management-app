-- Departments
INSERT INTO department (id, name, description, created_at) VALUES
(1, 'IT', 'Information Technology Department', CURRENT_TIMESTAMP),
(2, 'HR', 'Human Resources Department', CURRENT_TIMESTAMP),
(3, 'Finance', 'Finance and Accounting Department', CURRENT_TIMESTAMP),
(4, 'Marketing', 'Marketing and Sales Department', CURRENT_TIMESTAMP);

-- Employees
INSERT INTO employee (id, first_name, last_name, email, phone, position, department_id, role, date_hired, status, created_at) VALUES
(1, 'John', 'Doe', 'john.doe@company.com', '1234567890', 'Senior Developer', 1, 'DEVELOPER', '2023-01-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(2, 'Jane', 'Smith', 'jane.smith@company.com', '2345678901', 'HR Manager', 2, 'MANAGER', '2023-02-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(3, 'Michael', 'Johnson', 'michael.j@company.com', '3456789012', 'Finance Director', 3, 'DIRECTOR', '2023-03-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(4, 'Emily', 'Brown', 'emily.b@company.com', '4567890123', 'Marketing Specialist', 4, 'SPECIALIST', '2023-04-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(5, 'David', 'Wilson', 'david.w@company.com', '5678901234', 'Junior Developer', 1, 'DEVELOPER', '2023-05-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(6, 'Sarah', 'Davis', 'sarah.d@company.com', '6789012345', 'HR Assistant', 2, 'ASSISTANT', '2023-06-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(7, 'James', 'Miller', 'james.m@company.com', '7890123456', 'Accountant', 3, 'ACCOUNTANT', '2023-07-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(8, 'Lisa', 'Taylor', 'lisa.t@company.com', '8901234567', 'Marketing Manager', 4, 'MANAGER', '2023-08-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(9, 'Robert', 'Anderson', 'robert.a@company.com', '9012345678', 'System Admin', 1, 'ADMIN', '2023-09-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(10, 'Emma', 'Thomas', 'emma.t@company.com', '0123456789', 'Financial Analyst', 3, 'ANALYST', '2023-10-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(11, 'Admin', 'Admin', 'admin@company.com', '2134234', 'ADMIN', 3, 'ADMIN', '2023-10-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
(12, 'User', 'User', 'user@company.com', '5324534', 'USER', 3, 'USER', '2023-10-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP);

-- Users (password is 'password' - in real application should be properly hashed)
INSERT INTO app_users (id, username, password, failed_attempt, account_non_expired, account_non_locked, credentials_non_expired, lock_time, last_login, password_reset_token, reset_token_expiry, employee_id, created_at, updated_at, status) VALUES
(1, 'john_doe', 'password123', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(2, 'jane_smith', 'password456', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(3, 'alice_wonder', 'password789', 1, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(4, 'bob_builder', 'password321', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(5, 'charlie_choco', 'password654', 2, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(6, 'dave_daring', 'password987', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(7, 'eve_explorer', 'password111', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(8, 'frank_friendly', 'password222', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(9, 'grace_gritty', 'password333', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(10, 'helen_hero', 'password444', 1, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(11, 'admin', 'admin', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE'),
(12, 'user', 'user', 0, TRUE, TRUE, TRUE, NULL, NULL, NULL, NULL, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');



--User Roles
INSERT INTO user_roles (user_id, role) VALUES
(1, 'USER'),
(1, 'ADMIN'),
(2, 'USER'),
(3, 'MODERATOR'),
(4, 'USER'),
(5, 'USER'),
(5, 'MODERATOR'),
(6, 'USER'),
(7, 'USER'),
(8, 'ADMIN'),
(9, 'USER'),
(10, 'USER'),
(10, 'MODERATOR'),
(12, 'EMPLOYEE'),
(11, 'ADMIN');

-- Sample Schedules
INSERT INTO schedule (employee_id, date, shift_start_time, shift_end_time, description, created_at) VALUES
(1, CURRENT_DATE, '09:00:00', '18:00:00', 'Regular Shift', CURRENT_TIMESTAMP),
(2, CURRENT_DATE, '09:00:00', '18:00:00', 'Regular Shift', CURRENT_TIMESTAMP),
(3, CURRENT_DATE, '09:00:00', '18:00:00', 'Regular Shift', CURRENT_TIMESTAMP);

-- Sample Attendance
INSERT INTO attendance (employee_id, date, time_in, time_out, status) VALUES
(1, CURRENT_DATE, CURRENT_TIMESTAMP, NULL, 'PRESENT'),
(2, CURRENT_DATE, CURRENT_TIMESTAMP, NULL, 'PRESENT'),
(3, CURRENT_DATE, CURRENT_TIMESTAMP, NULL, 'PRESENT');

-- Sample Leave Requests
INSERT INTO leave_request (employee_id, start_date, end_date, reason, status, applied_date, approved_by, created_at) VALUES
(4, DATEADD('DAY', 1, CURRENT_DATE), DATEADD('DAY', 3, CURRENT_DATE), 'Annual Leave', 'PENDING', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(5, DATEADD('DAY', 5, CURRENT_DATE), DATEADD('DAY', 7, CURRENT_DATE), 'Family Event', 'APPROVED', CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP);

UPDATE app_users SET 
    created_at = '2024-01-01 09:00:00',
    updated_at = '2024-01-01 09:00:00',
    last_login = '2024-01-06 17:00:00',
    failed_attempt = 0
WHERE id IN (1,2,3,4,5,6,7,8,9,10,11,12);
