package cucumber.features;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	@Given("^I navigated to zoo website$")
	public void I_navigated_to_zoo_website() throws Throwable{
	System.out.println("executed the navigate to zoo method");
	}
	
	@When("^I click on the link$")
	public void i_click_on_the_link() throws Throwable{
		System.out.println("executed the click on adoption link method");
	}
	@Then("^I check to see that no animals are available$")
	public void I_check_to_see_that_no_animals_are_available() throws Throwable{
		System.out.println("check that there is no animal");
	}
}
