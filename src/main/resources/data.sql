-- division
INSERT IGNORE INTO
    tbl_division (id, division_name)
VALUES
    (@division_hr := UUID(), 'Human Resource (HR)');

-- roles
INSERT IGNORE INTO
    tbl_app_role(id, rolename)
VALUES
    (@role_hr := UUID(), 'HR'),
    (@role_user := UUID(), 'USER'),
    (@role_svp := UUID(), 'SVP'),
    (@role_mgr := UUID(), 'MGR');

-- HR account (HR12356)
INSERT IGNORE INTO
    tbl_app_user(id, username, full_name, email_address, division_id, position, employee_status, password, join_date)
VALUES
    (@user_hr := UUID(), 'HR01', 'Ini Akun HR', 'hr@gmail.com',
     @division_hr, 'Human Resource', 1,
     '$2y$10$4cHJO44wGKnT.keIR6mMxeKlkX5sMnjwQ2eHIbdhFL4AIZRLwvaSm', '2023-01-01');

-- add HR role to HR account
INSERT IGNORE INTO
    tbl_app_user_role(id, user_id, role_id)
VALUES
    (UUID(), @user_hr, @role_hr);


-- menu list
INSERT IGNORE INTO tbl_app_menu(id, menu_name) VALUES 
(@menu_user_all := UUID(), 'user#all'),
(@menu_division_all := UUID(), 'division#all'),
(@menu_role_menu_all := UUID(), 'role-menu#all'),
(@menu_group_attitude_skill_all := UUID(), 'group-attitude-skill#all'),
(@menu_attitude_skill_all := UUID(), 'attitude-skill#all'),
(@menu_technical_skill_all := UUID(), 'technical-skill#all'),
(@menu_dev_plan_all := UUID(), 'dev-plan#all'),
(@menu_group_achievement_all := UUID(), 'group-achievement#all'),
(@menu_achievement_all := UUID(), 'achievement#all'),
(@menu_summary_read := UUID(), 'summary#read'),
(@menu_summary_read_self := UUID(), 'summary#read.self'),
(@menu_emp_achievement_all := UUID(), 'emp-achievement#all'),
(@menu_emp_attitude_skill_all := UUID(), 'emp-attitude-skill#all'),
(@menu_emp_technical_skill_all := UUID(), 'emp-technical-skill#all'),
(@menu_emp_dev_plan_all := UUID(), 'emp-dev-plan#all'),
(@menu_emp_suggestion_all := UUID(), 'emp-suggestion#all'),
(@menu_user_read := UUID(), 'user#read');

-- initial role-menu
-- HR role menu mappings
INSERT IGNORE INTO tbl_app_role_menu (id, role_id, menu_id)
VALUES
(UUID(), @role_hr, @menu_user_all),
(UUID(), @role_hr, @menu_division_all),
(UUID(), @role_hr, @menu_role_menu_all),
(UUID(), @role_hr, @menu_group_attitude_skill_all),
(UUID(), @role_hr, @menu_attitude_skill_all),
(UUID(), @role_hr, @menu_technical_skill_all),
(UUID(), @role_hr, @menu_dev_plan_all),
(UUID(), @role_hr, @menu_group_achievement_all),
(UUID(), @role_hr, @menu_emp_achievement_all),
(UUID(), @role_hr, @menu_summary_read),
(UUID(), @role_hr, @menu_achievement_all);

-- User role menu mappings
INSERT IGNORE INTO tbl_app_role_menu (id, role_id, menu_id)
VALUES
(UUID(), @role_user, @menu_emp_attitude_skill_all),
(UUID(), @role_user, @menu_emp_technical_skill_all),
(UUID(), @role_user, @menu_emp_dev_plan_all),
(UUID(), @role_user, @menu_emp_suggestion_all),
(UUID(), @role_user, @menu_summary_read_self);

-- SVP role menu mappings
INSERT IGNORE INTO tbl_app_role_menu (id, role_id, menu_id)
VALUES
(UUID(), @role_svp, @menu_summary_read),
(UUID(), @role_svp, @menu_user_read);

-- MGR role menu mappings
INSERT IGNORE INTO tbl_app_role_menu (id, role_id, menu_id)
VALUES
(UUID(), @role_mgr, @menu_summary_read),
(UUID(), @role_mgr, @menu_user_read);