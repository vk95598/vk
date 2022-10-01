@store
Feature: (e2e) Validate store

  @getInventoryStore
  Scenario: (e2e) Validate that the response has the inventory
    Given the following breeding application that brings us the inventory
    Then the response is 200 for inventory

  @postOrder
  Scenario Outline: (e2e) Validate that the response has a order
    Given the following post request that add order
    And the response is 200 for the post order
    Then the body response contains update order "<complete>"

    Examples:
      | complete |
      | true     |

  @getOrderId
  Scenario: (e2e) Validate that the response has the order by id
    Given the following get request brings us the created order
    Then the response is 200 for order by id


  @deleteOrder
  Scenario: (e2e) Validate that the response has not the order deleted
    Given the following delete request that delete a order
    Given the response is 200 for delete order