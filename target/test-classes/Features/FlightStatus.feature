Feature: Eurowings flight status check
 
Background: 
   Given User is checking flight statuses on "https://www.eurowings.com/en/information/at-the-airport/flight-status.html"
  
   @ViaFlightRoute
   Scenario Outline: Verify flight details using routes
      
    When User enters departure as "<depart>" and destination as "<dest>" and date as "<date>"
    Then User should be able to see "<date>" flights
    And Route must be mentioned from "<depart>" to "<dest>"
    And status should be mentioned as "<Status 1>" or "<Status 2>"
          
  Examples:
  | depart   | dest  | date      | Status 1	| Status 2 |
  | CGN      | BER   | Today     | arrived 	| on time  |
  | CGN      | BER   | Tomorrow  | on time  | on time  |
  | CGN      | BER   | Yesterday | arrived  | arrived  |
  
   @ViaFlightNumber
   Scenario: Verify flight details using the flight number
      
    When User enters flight number (fetched from routes) and date
    Then User should be able to see the exact flight
    

