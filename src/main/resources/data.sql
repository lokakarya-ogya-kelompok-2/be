-- roles
INSERT IGNORE INTO
    tbl_app_role(id, rolename)
VALUES
    (UUID(), 'HR'),
    (UUID(), 'USER'),
    (UUID(), 'SVP'),
    (UUID(), 'MGR');

-- HR account
INSERT IGNORE INTO
    tbl_app_user(id, username, full_name, email_address, position, employee_status, password, join_date)
VALUES
    (UUID(), 'HR01', 'Ini Akun HR', 'hr@gmail.com', 'Human Resource', 1, '123', '2023-01-01');

-- add HR role to HR account
INSERT IGNORE INTO
    tbl_app_user_role(id, user_id, role_id)
VALUES
    (UUID(), (SELECT id FROM tbl_app_user WHERE username = 'HR01'), (SELECT id FROM tbl_app_role WHERE rolename = 'HR'));