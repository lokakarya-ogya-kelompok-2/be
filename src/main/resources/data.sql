-- division
INSERT INTO tbl_division (id, division_name)
SELECT UUID(), 'Human Resource (HR)'
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

-- HR account (HR12356)
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


-- menu list
INSERT IGNORE INTO tbl_app_menu(id, menu_name) VALUES 
(UUID(), 'user#create'),
(UUID(), 'user#read'),
(UUID(), 'user#update'),
(UUID(), 'user#delete'),
(UUID(), 'division#create'),
(UUID(), 'division#read'),
(UUID(), 'division#update'),
(UUID(), 'division#delete'),
(UUID(), 'role_menu#create'),
(UUID(), 'role_menu#read'),
(UUID(), 'role_menu#update'),
(UUID(), 'role_menu#delete'),
(UUID(), 'group_attitude_skill#create'),
(UUID(), 'group_attitude_skill#read'),
(UUID(), 'group_attitude_skill#update'),
(UUID(), 'group_attitude_skill#delete'),
(UUID(), 'attitude_skill#create'),
(UUID(), 'attitude_skill#read'),
(UUID(), 'attitude_skill#update'),
(UUID(), 'attitude_skill#delete'),
(UUID(), 'group_technical_skill#create'),
(UUID(), 'group_technical_skill#read'),
(UUID(), 'group_technical_skill#update'),
(UUID(), 'group_technical_skill#delete'),
(UUID(), 'technical_skill#create'),
(UUID(), 'technical_skill#read'),
(UUID(), 'technical_skill#update'),
(UUID(), 'technical_skill#delete'),
(UUID(), 'dev_plan#create'),
(UUID(), 'dev_plan#read'),
(UUID(), 'dev_plan#update'),
(UUID(), 'dev_plan#delete'),
(UUID(), 'group_achievement#create'),
(UUID(), 'group_achievement#read'),
(UUID(), 'group_achievement#update'),
(UUID(), 'group_achievement#delete'),
(UUID(), 'achievement#create'),
(UUID(), 'achievement#read'),
(UUID(), 'achievement#update'),
(UUID(), 'achievement#delete'),
(UUID(), 'summary#create'),
(UUID(), 'summary#read'),
(UUID(), 'summary#update'),
(UUID(), 'summary#delete'),
(UUID(), 'emp_attitude_skill#create'),
(UUID(), 'emp_attitude_skill#read'),
(UUID(), 'emp_attitude_skill#update'),
(UUID(), 'emp_attitude_skill#delete'),
(UUID(), 'emp_achievement_skill#create'),
(UUID(), 'emp_achievement_skill#read'),
(UUID(), 'emp_achievement_skill#update'),
(UUID(), 'emp_achievement_skill#delete'),
(UUID(), 'emp_technical_skill#create'),
(UUID(), 'emp_technical_skill#read'),
(UUID(), 'emp_technical_skill#update'),
(UUID(), 'emp_technical_skill#delete'),
(UUID(), 'emp_suggestion#create'),
(UUID(), 'emp_suggestion#read'),
(UUID(), 'emp_suggestion#update'),
(UUID(), 'emp_suggestion#delete'),
(UUID(), 'emp_dev_plan#create'),
(UUID(), 'emp_dev_plan#read'),
(UUID(), 'emp_dev_plan#update'),
(UUID(), 'emp_dev_plan#delete');