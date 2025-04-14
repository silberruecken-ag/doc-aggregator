Feature: Documentation search

  Scenario: A user searches for a documentation snippet
    Given a documentation with text "hello world"
    When a user searches for a documentation with query "world"
    Then he should get one result

  Scenario: A user searches for a non-existig documentation snippet
    Given a documentation with text "hello world"
    When a user searches for a documentation with query "bern"
    Then he should get one result