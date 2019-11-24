package com.qa.TestPackage;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseClass.BaseClass;

public class CustomerHelpPage extends BaseClass
{
	
	@FindBy(xpath="//a[@id='nav-link-shopall']//span[@class='nav-icon nav-arrow']")
	WebElement Dept;
	
	@FindBy(linkText="Baby")
	WebElement DropDownlink;
	
	@FindBy(xpath="//a[@id='nav-link-accountList']//span[@class='nav-line-2']")
	WebElement AccountnList;
	
	@FindBy(linkText="Sign Out")
	WebElement SignOut;
	
	
	
	public CustomerHelpPage() 
	{
	PageFactory.initElements(driver, this);	
	}
	
	
	public void MouseHoverDept()
	{
		
				
        WebElement element = Dept;
        WebElement subelement = DropDownlink;
 
        Actions action = new Actions(driver);
 
       action.moveToElement(element).moveToElement(subelement).click().build().perform();
		
	}
	
	public void AmazonLogout()
	{
		
				
        WebElement element = AccountnList;
        WebElement subelement = SignOut;
 
        Actions action = new Actions(driver);
 
       action.moveToElement(element).moveToElement(subelement).click().build().perform();
		
	}
}
