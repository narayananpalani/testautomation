package com.qa.TestCase;



import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.BaseClass.BaseClass;
import com.qa.TestPackage.DashBoardPage;
import com.qa.lib.TakeScreenshot;


public class DashBoardPageTest extends BaseClass
{

	DashBoardPage Dash;

	public DashBoardPageTest()
	{
		super();
	}

	@BeforeTest 
	public void DashBoardIntialize()
	{
		Dash=new DashBoardPage();
	}

	@Test(priority=4,description="Search and get the list of all Footwear along with price on amazon Page")
	public void AmazonSearchPage() throws Exception 
	{
		logger1 = extent.createTest("Footwear Serach on Amzaon Page");
		Thread.sleep(2000);
		Dash.FootwearSearch();
		TakeScreenshot.captuerScreenshot(driver, "FootwearSearch");

	}

	@Test(priority=5,description="Search and get the list of all Footwear along with price on amazon Page")
	public void ClickonContactPage() throws Exception 
	{
		logger1 = extent.createTest("Click on Contact Us Page on Amazon");
		Dash.ClickonContactUS();
		String Tittle=Dash.ContactUsTittleValidation();
		Assert.assertEquals(Tittle, "International Shopping: Help @ Amazon.com");
		Dash.CustomerHelpPage();
		logger.info("Moved to Customer Help Page");
		TakeScreenshot.captuerScreenshot(driver, "ContactUs");

	}
	


}
