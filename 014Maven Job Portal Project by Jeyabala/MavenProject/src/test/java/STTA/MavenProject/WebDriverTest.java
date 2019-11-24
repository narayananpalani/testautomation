package STTA.MavenProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class WebDriverTest {
 WebDriver driver;

  @Test
  public void verifySearch() {
   driver = new FirefoxDriver();
   //driver.get("http://only-testing-blog.blogspot.in/");
   driver.get("http://www.indeed.co.uk/");
	
	//Searching Job
	driver.findElement(By.xpath("id('what')")).sendKeys("Teaching Assistant");
	driver.findElement(By.xpath("id('where')")).clear();
	driver.findElement(By.xpath("id('where')")).sendKeys("Kent");
	driver.findElement(By.xpath("id('fj')")).click();
	
	//Logging in 
	driver.findElement(By.xpath("id('userOptionsLabel')")).click();
	driver.findElement(By.xpath("id('signin_email')")).clear();
	driver.findElement(By.xpath("id('signin_email')")).sendKeys("jeyabalavicapri@gmail.com");
	
	driver.findElement(By.xpath("id('signin_password')")).clear();
	driver.findElement(By.xpath("id('signin_password')")).sendKeys("passwordvicapri");
	
	driver.findElement(By.xpath("id('loginform')/div[2]/table/tbody/tr/td[1]/input")).click();
	//docfilepath= "C:\\Jeyabala\\from old PC\\Recovered Data\\Jeyabala\\IT_jobsearch\\Jeyabala George_CV_admin.doc";
	
	
	//driver.findElement(By.xpath("id('sja2')")).click();
	//driver.findElement(By.xpath("id('jl_6bc1ad552a47eb6b')/a")).click();
	driver.findElement(By.xpath("id('jl_817af8cf12e44983')/a")).click();
	driver.findElement(By.xpath("id('indeed_apply')")).click();
	driver.navigate();
	
	//String handle=driver.getWindowHandle();
	//driver.findElement(By.xpath("id('indeed-ia-1443740514044-1label')")).click();
	driver.findElement(By.xpath("id('indeed-ia-1443790421489-0label')")).click();
	WebElement ifr=driver.findElement(By.xpath("id('indeed_apply')"));
	driver.switchTo().frame(ifr);
	driver.findElement(By.xpath("id('rezLink')")).click();
	 
	
	//driver.findElement(By.xpath("id('indeed-ia-1443735911943-1label')")).click();
	
	
	//driver.findElement(By.xpath("id('form_container')/div[2]/div[1]/div[1]/p/a")).click();
	//driver.findElement(By.xpath("id('indeed-ia-1443737097094-1label')")).click();
			
	
	/*driver.findElement(By.xpath("id('indeedapply-modal-preload-branding')")).click();
	
	driver.findElement(By.xpath("id('form_container')/div[2]/div[1]/div[1]/p/a")).click();
	driver.findElement(By.xpath("id('resume')")).click();
	driver.findElement(By.id("resume")).sendKeys(docfilepath);*/
		
	
	//driver.findElement(By.id(docfilepath));

			
	/*driver.setFileDetector(new LocalFileDetector());
	WebElement upload = driver.findElement(By.id("myfile"));
	upload.sendKeys("/Users/sso/the/local/path/to/darkbulb.jpg");
	driver.findElement(By.id("submit")).click();
	*/

   driver.quit();
  }
}