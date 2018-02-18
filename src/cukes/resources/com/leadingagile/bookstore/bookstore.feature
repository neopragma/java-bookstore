Feature: Querying and adding books and authors

  Scenario Outline: Adding authors to the system
    Given no authors are defined
    When I add an author with display name <display_name>, surname <surname>, given name <given_name>, and middle name <middle_name>
    Then the system will return information about that author

    Examples:
      | display_name  | surname | given_name | middle_name |
      | Dr Seuss     | Adams   | John       | Quincy      |
      | Word Wrangler | Lantana | Susanna    | Malana      |
      | Ed Mills      | Mills   | Edward     | Thomas      |

