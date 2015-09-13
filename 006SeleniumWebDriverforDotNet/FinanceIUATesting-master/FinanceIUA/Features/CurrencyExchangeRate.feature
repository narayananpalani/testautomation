Feature: Currency Exchange Rate in Ukraine 
	In order to avoid silly mistakes
	As a anonimous user
	I want to check average USD exchange rate of banks in Ukraine

@mytag
Scenario: average USD exchange rate of banks in Ukraine 
	Given I have opened the Finance page 
	And I have grabded average "USD" rate in Average Rate section
	Then I set "USD" currency in Ukrainian Banks rates section
	When I compare calculated and grabbed values the result should be equal to "4" decimal
