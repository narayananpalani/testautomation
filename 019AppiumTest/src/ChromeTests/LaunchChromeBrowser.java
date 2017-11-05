package ChromeTests;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LaunchChromeBrowser {

@Test
public static void main(String[] args) throws MalformedURLException, InterruptedException{
	DesiredCapabilities capabilities=DesiredCapabilities.android();
	capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.CHROME);
	capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);
	capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
	capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Narayanan Palani");
    //capabilities.setCapability("device","Android");
    //capabilities.setCapability("app", "Chrome");
    capabilities.setCapability(MobileCapabilityType.VERSION, "4.4.2");
    //WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    //driver.wait(2000);
    //driver.get("http://www.yahoo.com");
    URL url=new URL("http://127.0.0.1:4723/wd/hub");
    WebDriver driver=new AndroidDriver(url,capabilities);
    driver.get("http://www.yahoo.com");
	//driver.get("https://www.google.co.in/?gws_rd=ssl");
	//driver.findElement(By.id("lst-ib")).sendKeys("Selenium Webdriver");
	//driver.findElement(By.name("btnG")).click();
	//driver.findElement(By.name("hdtb-msb/[3]/a")).click();
    
    
    
}
	
}
