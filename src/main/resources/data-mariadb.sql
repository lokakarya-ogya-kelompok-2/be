-- division
INSERT IGNORE INTO tbl_division (id, division_name)
SELECT UUID(), 'Human Resource (HR)'
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM tbl_division WHERE division_name = 'Human Resource (HR)'
);

-- roles
INSERT IGNORE INTO
    tbl_app_role(id, rolename)
VALUES
    (UUID(), 'HR'),
    (UUID(), 'USER'),
    (UUID(), 'SVP'),
    (UUID(), 'MGR');

-- HR account (HR123456)
INSERT IGNORE INTO
    tbl_app_user(id, username, full_name, email_address, division_id, position, employee_status, password, join_date)
VALUES
    (UUID(), 'HR01', 'Ini Akun HR', 'hr@gmail.com',
     (SELECT id FROM tbl_division WHERE division_name = 'Human Resource (HR)' LIMIT 1), 'Human Resource', 1,
     '$2y$10$4cHJO44wGKnT.keIR6mMxeKlkX5sMnjwQ2eHIbdhFL4AIZRLwvaSm', '2023-01-01');

-- add HR role to HR account
INSERT IGNORE INTO
    tbl_app_user_role(id, user_id, role_id)
VALUES
    (UUID(), (SELECT id FROM tbl_app_user WHERE username = 'HR01' LIMIT 1),
     (SELECT id FROM tbl_app_role WHERE rolename = 'HR' LIMIT 1));