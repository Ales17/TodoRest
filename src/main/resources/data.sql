INSERT INTO company (id, name)
VALUES (1, 'Boni Global Company'),
       (2, 'HealthPlus Inc.'),
       (3, 'EcoBuild Industries'),
       (4, 'FinTech Partners'),
       (5, 'TechCorp Solutions');

INSERT INTO user_entity (id, username, role, company_id)
VALUES (1, 'superuser', 2, NULL),
       (2, 'pnewton', 0, 2),
       (3, 'alittle', 1, 2),
       (4, 'swhite', 0, 3);


INSERT INTO task (id, name, description, user_id, completed)
VALUES (1, 'Complete Project Report', 'Finalize the project report and send it to the manager.', 1, true),
       (2, 'Prepare Presentation', 'Create slides for the upcoming client meeting.', 2, false),
       (3, 'Team Meeting', 'Attend the weekly team sync-up at 10:00 AM.', 3, true),
       (4, 'Update Website Content', 'Review and update the company website’s About page.', 4, true),
       (5, 'Follow Up with Client', 'Call the client to confirm project requirements.', 1, true),
       (6, 'Plan Marketing Campaign', 'Outline the marketing strategy for the next quarter.', 2, false),
       (7, 'Fix Bugs in Application', 'Resolve reported issues in the mobile app.', 3, false),
       (8, 'Conduct Code Review', 'Review the pull requests submitted by the team.', 4, true);

