-- Departments
INSERT INTO department (name, description, created_at) VALUES
('IT', 'Information Technology Department', CURRENT_TIMESTAMP),
('HR', 'Human Resources Department', CURRENT_TIMESTAMP),
('Finance', 'Finance and Accounting Department', CURRENT_TIMESTAMP),
('Marketing', 'Marketing and Sales Department', CURRENT_TIMESTAMP);

-- Employees
INSERT INTO employee (first_name, last_name, email, phone, position, department_id, role, date_hired, status, created_at) VALUES
('John', 'Doe', 'john.doe@company.com', '1234567890', 'Senior Developer', 1, 'DEVELOPER', '2023-01-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Jane', 'Smith', 'jane.smith@company.com', '2345678901', 'HR Manager', 2, 'MANAGER', '2023-02-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Michael', 'Johnson', 'michael.j@company.com', '3456789012', 'Finance Director', 3, 'DIRECTOR', '2023-03-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Emily', 'Brown', 'emily.b@company.com', '4567890123', 'Marketing Specialist', 4, 'SPECIALIST', '2023-04-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('David', 'Wilson', 'david.w@company.com', '5678901234', 'Junior Developer', 1, 'DEVELOPER', '2023-05-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Sarah', 'Davis', 'sarah.d@company.com', '6789012345', 'HR Assistant', 2, 'ASSISTANT', '2023-06-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('James', 'Miller', 'james.m@company.com', '7890123456', 'Accountant', 3, 'ACCOUNTANT', '2023-07-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Lisa', 'Taylor', 'lisa.t@company.com', '8901234567', 'Marketing Manager', 4, 'MANAGER', '2023-08-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Robert', 'Anderson', 'robert.a@company.com', '9012345678', 'System Admin', 1, 'ADMIN', '2023-09-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP),
('Emma', 'Thomas', 'emma.t@company.com', '0123456789', 'Financial Analyst', 3, 'ANALYST', '2023-10-01 09:00:00', 'ACTIVE', CURRENT_TIMESTAMP);

-- Users (password is 'password' - in real application should be properly hashed)
INSERT INTO app_users (user_name, password, role, employee_id, status, created_at) VALUES
('john.doe', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'ADMIN', 1, 'ACTIVE', CURRENT_TIMESTAMP),
('jane.smith', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'MANAGER', 2, 'ACTIVE', CURRENT_TIMESTAMP),
('michael.j', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'MANAGER', 3, 'ACTIVE', CURRENT_TIMESTAMP),
('emily.b', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'EMPLOYEE', 4, 'ACTIVE', CURRENT_TIMESTAMP),
('david.w', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'EMPLOYEE', 5, 'ACTIVE', CURRENT_TIMESTAMP),
('sarah.d', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'EMPLOYEE', 6, 'ACTIVE', CURRENT_TIMESTAMP),
('james.m', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'EMPLOYEE', 7, 'ACTIVE', CURRENT_TIMESTAMP),
('lisa.t', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'MANAGER', 8, 'ACTIVE', CURRENT_TIMESTAMP),
('robert.a', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'ADMIN', 9, 'ACTIVE', CURRENT_TIMESTAMP),
('emma.t', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'EMPLOYEE', 10, 'ACTIVE', CURRENT_TIMESTAMP);

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

INSERT INTO user_permission (user_id, permission) VALUES
(1, 'READ_ALL'),
(1, 'WRITE_ALL'),
(1, 'DELETE_ALL'),
(2, 'READ_DEPARTMENT'),
(2, 'WRITE_DEPARTMENT'),
(3, 'READ_DEPARTMENT'),
(3, 'WRITE_DEPARTMENT'),
(8, 'READ_DEPARTMENT'),
(8, 'WRITE_DEPARTMENT'),
(9, 'READ_ALL'),
(9, 'WRITE_ALL'),
(9, 'DELETE_ALL');

UPDATE app_users SET 
    created_at = '2024-01-01 09:00:00',
    updated_at = '2024-01-01 09:00:00',
    last_login = '2024-01-06 17:00:00',
    failed_attempt = 0
WHERE id IN (1,2,3,4,5,6,7,8,9,10);
