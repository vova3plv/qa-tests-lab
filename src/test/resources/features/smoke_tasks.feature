Feature: Smoke tests for task creation

  Background:
    Given the task management system is initialized

  Scenario: SMK-01 Owner can create a valid task
    Given a user "u1" with role "OWNER"
    And a task with title "Task 1" and description "OK" and deadline offset 1 days and ownerId "u1"
    When the user tries to create the task
    Then the task is created successfully
    And repository count should be 1

  Scenario: SMK-02 Observer cannot create a task
    Given a user "u2" with role "OBSERVER"
    And a task with title "Task 2" and description "OK" and deadline offset 1 days and ownerId "u2"
    When the user tries to create the task
    Then creation fails with "SecurityException"
    And repository count should be 0

  Scenario Outline: SMK-03 Invalid task data should be rejected
    Given a user "u1" with role "OWNER"
    And a task with title "<title>" and description "<desc>" and deadline offset <days> days and ownerId "u1"
    When the user tries to create the task
    Then creation fails with "IllegalArgumentException"
    And repository count should be 0

    Examples:
      | title   | desc     | days |
      |         | OK       | 1    |
      |     | OK       | 1    |
      | Task X  | LONG_501 | 1    |
      | Task X  | OK       | -1   |
