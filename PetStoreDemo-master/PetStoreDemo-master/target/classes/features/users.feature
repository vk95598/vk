@users
Feature: (e2e) Validate users

  @postUser
  Scenario Outline: (e2e) Validate post one user
    Given the following post request that add one user
    And the response is 200 for the post user
    And the body response contains key "<id>"
    Then the body response contains the "<message>" of the user created

    Examples:
      | id     | message |
      | 100510 | 100510  |


  @postUserList
  Scenario: (e2e) Validate post create users with list
    Given the following post request that create with a list
    And the response is 200 for the post users list
    Then the body response for the POST request that create using a list contains ok


  @getUsers
  Scenario Outline: (e2e) Validate that the response has the new user
    Given the following get request which brings us "<username>"
    Then the response is 200 for the get user
    Examples:
      | username    |
      | georgeLucas |

  @userLogin
  Scenario Outline: (e2e) Validate login into the system
    Given the user login with "<username>" and "<password>"
    Then the response is 200 for login

    Examples:
      | username    | password |
      | georgeLucas | wookiee  |

  @userLogout
  Scenario: (e2e) Validate logout current logged in user session
    Given the user logout the current session
    Then the response is 200 and message is ok

  @postUser
  Scenario: (e2e) Validate updated user
    Given the following put request that update users
    Then the response is 200 for the update

  @deleteUser
  Scenario: (e2e) Validate deleted user
    Given the following post request that add one user
    And following delete request that delete user
    Then the body response is empty