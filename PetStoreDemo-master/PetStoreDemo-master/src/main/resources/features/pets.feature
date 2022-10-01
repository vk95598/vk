@pets
Feature: (e2e) Validate pets

  @getAllPets
  Scenario: (e2e) Validate that the response of the pets request is 200
    Given the following get request that brings us the pets list
    Then the response is 200

  @getPetsAvailable
  Scenario: (e2e) Validate that pets are in available status
    Given the following get request that brings us the pets availables
    Then the response is 200 for availables


  @postPet
  Scenario Outline: (e2e) Validate post new pet
    Given the following post that add pet
    And the response is 200 for the post pet
    Then the body response contains the "<name>" of the pet created

    Examples:
      | name |
      | doggie |

  @putPet
  Scenario Outline: (e2e) Validate update a pet
    Given the following put request that update a pet
    And the response is 200 for the put pet
    Then the body response contains update "<updated_status>"

    Examples:
      | updated_status |
      | sold           |


  @deletePet
  Scenario: (e2e) Validate delete a pet
    Given the following post that add pet
    And the following delete request that delete a pet
    Then the body response is 200 for the delete pet