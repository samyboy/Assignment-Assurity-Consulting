Feature: Verify Categories API of TMS Sandbox

  @getEndpoints
  @regression
  Scenario: validate that GET endpoint to fetch meta-data of categories from CategoriesAPI returns data
  
    Given Get catalog payload
    When User calls "GetDetailsAPI" with "Get" http request
    Then The API is successful with status code 200
    And "Name" in response is "Carbon credits"
    And "CanRelist" in response is 'true'
    And "Promotions" in response is having "Description" as "Good position in category" where "Name" is "Gallery"

