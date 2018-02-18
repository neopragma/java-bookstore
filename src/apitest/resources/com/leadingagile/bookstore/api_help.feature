Feature: Bookstore API discovery

  Scenario: Client system discovers Bookstore API

    Given client wants to know how to interact with the Bookstore API
    When client calls the base URI
    Then client receives API help



