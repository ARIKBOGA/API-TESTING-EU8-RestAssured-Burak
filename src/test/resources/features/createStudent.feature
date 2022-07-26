Feature: Create student


  @wip
  Scenario: Create student a teacher and verify status code 201
    Given I logged BookIt api using "teacherilsamnickel@gmail.com" and "samnickel"
    When I send POST request to "/api/students/student" endpoint with following information
      | first-name      | muhtar               |
      | last-name       | java                 |
      | email           | muhtarjava@gmail.com |
      | password        | abc123               |
      | role            | student-team-leader  |
      | campus-location | VA                   |
      | batch-number    | 8                    |
      | team-name       | Nukes                |
    Then status code should be 201
    And I delete previously added student


  Scenario: test config
    Given I get env properties


  Scenario: Create student a teacher and verify status code 201
    Given I logged BookIt api as "teacher"
    When I send POST request to "/api/students/student" endpoint with following information
      | first-name      | wooden                |
      | last-name       | spoon                 |
      | email           | woodenspoon@gmail.com |
      | password        | abc123                |
      | role            | student-team-leader   |
      | campus-location | VA                    |
      | batch-number    | 7                     |
      | team-name       | BugBusters            |
    Then status code should be 201
    And I delete previously added student

