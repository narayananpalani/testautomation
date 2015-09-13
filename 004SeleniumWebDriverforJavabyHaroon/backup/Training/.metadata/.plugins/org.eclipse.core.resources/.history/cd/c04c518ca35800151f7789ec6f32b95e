package Testjunit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Mytest {
 
 WebDriver driver = new FirefoxDriver();

 @Test
 public void test() {
  driver.manage().window().maximize();
  System.out.print("Window maximise");
  driver.get("http://only-testing-blog.blogspot.in/");
  System.out.print("Site Open");
  driver.quit();
  System.out.print("End of Test");
 }

}