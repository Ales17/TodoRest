INSERT INTO user_entity (id, username, role)
VALUES (1, 'jsmith', 0),
       (2, 'pnewton', 0),
       (3, 'alittle', 1),
       (4, 'swhite', 2);

INSERT INTO task (id, name, description, user_id)
VALUES
    (1, 'Complete Project Report', 'Finalize the project report and send it to the manager.', 1),
    (2, 'Prepare Presentation', 'Create slides for the upcoming client meeting.', 2),
    (3, 'Team Meeting', 'Attend the weekly team sync-up at 10:00 AM.', 3),
    (4, 'Update Website Content', 'Review and update the company website’s About page.', 4),
    (5, 'Follow Up with Client', 'Call the client to confirm project requirements.', 1),
    (6, 'Plan Marketing Campaign', 'Outline the marketing strategy for the next quarter.', 2),
    (7, 'Fix Bugs in Application', 'Resolve reported issues in the mobile app.', 3),
    (8, 'Conduct Code Review', 'Review the pull requests submitted by the team.', 4);
