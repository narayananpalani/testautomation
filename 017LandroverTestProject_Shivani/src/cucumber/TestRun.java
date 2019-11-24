package cucumber;
//import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
@RunWith(Cucumber.class)
@Cucumber.Options(format = {"pretty","html:target/cucumber-html-report", "json:target/cucumber-report.json"},
                  features = {"src/cucumber/testlandrover.feature"})


public class TestRun {

}
