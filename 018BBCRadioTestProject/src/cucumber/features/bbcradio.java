package cucumber.features;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.iphone.IPhoneDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

//import org.openqa.selenium.safari.SafariDriver;
import java.util.concurrent.TimeUnit;
//import com.sun.jna.platform.FileUtils;





//import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
//import cucumber.api.java.en.And;
//import cucumber.runtime.PendingException;
public class bbcradio {
	public WebDriver driver;
	@Before
	public void setUp() {
	    System.out.println("*******************");
	    System.out.println("launching Chrome browser");
	    //System.setProperty("webdriver.ie.driver", "E://AutomationProject//Training//LandroverTestProject//BrowserDrivers//IEDriverServer.exe");
	    System.setProperty("webdriver.chrome.driver", "E:/AutomationProject/TrainingBBCRadioTestProject/BrowserDrivers/ChromeDriver.exe");
	}

	@Given("^BBC Radio is open in ChromeDriver$")
	public void BBC_Radio_is_open_in_ChromeDriver()
	   {

		driver=new ChromeDriver();
	    driver.get("http://www.bbc.co.uk/radio");
	   }
	
	@Given("^I can see the radio nav$")
	public void I_can_see_the_radio_nav()
	   {
		driver.findElement(By.className("radionav__stations-link radionav-dropdown")).isEnabled();
		driver.findElement(By.className("radionav__categories-link radionav-dropdown")).isEnabled();
		driver.findElement(By.className("radionav__schedules-link radionav-dropdown")).isEnabled();
	   }
	
	@Given("^When I select \"([^\"]*)\" in the radio nav$")
	public void When_I_select_section_in_the_radio_nav(String Stations,String Categories,String Schedules)
	   {
		driver.findElement(By.linkText(Stations)).click();
		driver.findElement(By.linkText(Categories)).click();
		driver.findElement(By.linkText(Schedules)).click();
	   }
	
}
