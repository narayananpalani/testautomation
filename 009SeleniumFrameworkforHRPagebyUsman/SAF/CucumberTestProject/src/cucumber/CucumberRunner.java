package cucumber;
import org.junit.runner.RunWith;
import cucumber.api.junit.*;
@Runwith(Cucumber.class)
@Cucumber.Option(
		format = {"pretty,"json:target/cucumber.json"}
		features = {"src/cucumber/"}
		)
public class CucumberRunner {

}
