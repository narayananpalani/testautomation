package com.qa.TestPackage;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseClass.BaseClass;

public class DashBoardPage extends BaseClass
{

	@FindBy(xpath="//input[@id='twotabsearchtextbox']")
	WebElement Search;

	@FindBy(xpath="//div[@id='resultsCol']")
	List<WebElement> FootwearSearchResult;
	
	@FindBy(xpath="//a[@class='nav-imageHref']")
	WebElement ContactUs;
	

	
	public DashBoardPage()
	{
		PageFactory.initElements(driver, this);

	}

	public void FootwearSearch()
	{
		Search.sendKeys("footwear");
		Search.sendKeys(Keys.ENTER);
		List<WebElement> footwearList = FootwearSearchResult;

		for (WebElement result:footwearList)
		{
			System.out.println("Result of Footwear Search is========");
			logger.info(result.getText());     
			System.out.println("=====================");

		}

	}

	public void ClickonContactUS()
	{
		
		ContactUs.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,50)");
				
	}
	
	public String ContactUsTittleValidation()
	{
		return driver.getTitle(); 
			
	}
	
	public CustomerHelpPage CustomerHelpPage()
	{
		return new CustomerHelpPage();
		
	}
	
	


}
