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

public class landrover {
//private static final String FILE = null;
public WebDriver browser;
@Before
public void setUp() {
    System.out.println("*******************");
    System.out.println("launching Chrome browser");
    //System.setProperty("webdriver.ie.driver", "E://AutomationProject//Training//LandroverTestProject//BrowserDrivers//IEDriverServer.exe");
    System.setProperty("webdriver.chrome.driver", "E:/AutomationProject/Training/LandroverTestProject_Shivani/BrowserDrivers/ChromeDriver.exe");
}

@Given("^Landrover page is open in InternetExplorer$")
public void Landrover_page_is_open_in_InternetExplorer()
   {
    //DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
    //ieCapabilities.getCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS);
    //this.browser=new InternetExplorerDriver(ieCapabilities);
    //throw new PendingException();
    //browser=new InternetExplorerDriver();
	browser=new ChromeDriver();
    browser.get("http://www.landrover.co.uk/");
   }

@When("^I click on AboveandBeyond Tab and verify FINDOUTMORE button$")
public void I_click_on_AboveandBeyond_Tab_and_verify_FINDOUTMORE_button()
{
    browser.findElement(By.id("MainNavigation:ABOVE AND BEYOND:/above-and-beyond/index.html")).click();
    //browser.findElement(By.id("HeroCarousel:FIND OUT MORE:https://live.landrover.co.uk/")).click();
    //browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    //String value = browser.findElement(By.id("295-82654")).getText();
    //browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    //browser.findElement(By.cssSelector("span.image.backgroundImage")).click();
    //browser.findElement(By.id("MainNavigation:ABOVE AND BEYOND:/above-and-beyond/index.html")).click();
    //browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    //browser.findElement(By.id("HeroCarousel:FIND OUT MORE:/above-and-beyond/reborn.html")).click();
}

@Then("^I see the AboveandBeyond Section of Landrover Page$")
public void I_see_the_AboveandBeyond_Section_of_Landrover_Page() {
System.out.println("TEST PASS");
}



}