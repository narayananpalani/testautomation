package com.qa.TestPackage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseClass.BaseClass;


public class AmazonLoginPage extends BaseClass
{

	@FindBy(xpath="//span[contains(text(),'Hello. Sign in')]")
	WebElement HelloSignin;
	
	@FindBy(xpath="//span[@class='nav-sprite nav-logo-base']")
	WebElement Logo;
	
	@FindBy(xpath="//input[@id='ap_email']")
	WebElement Email;
	
	@FindBy(xpath="//input[@id='ap_password']")
	WebElement Password;
	
	@FindBy(xpath="//input[@id='signInSubmit']")
	WebElement SigninButton;
	
	public AmazonLoginPage() 
	{
	PageFactory.initElements(driver, this);	
	}
	
	public boolean LogoPresent()
	{
		return Logo.isDisplayed();
		
	}
	public String ConsumerPortalGetTittle()
	{
		return driver.getTitle();
		
	}
	
	public void AmazonLogin() throws InterruptedException
	{
		
		HelloSignin.click();
		Thread.sleep(1000);
		Email.sendKeys("ankur.jain.cs@gmail.com");
		Password.sendKeys("Manish42#");
		SigninButton.click();
		
	}
	
	public DashBoardPage DashboardPage()
	{
		return new DashBoardPage();
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
