package com.qa.lib;

import java.io.File;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class TakeScreenshot 
{
	

	public static String captuerScreenshot(WebDriver driver,String screenshotName) throws Exception
	{
	
			
		//Convert web driver object to TakeScreenshot

        try {
        	
        	//Convert web driver object to TakeScreenshot
        	
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			//Call getScreenshotAs method to create image file

			 File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

			//Move image file to new destination

			 File DestFile=new File("./Screenshot/"+screenshotName+System.currentTimeMillis()+".png");

			 //Copy file at destination

			 FileUtils.copyFile(SrcFile, DestFile);
			 
			 System.out.println("Screenshot Captured");
			 
			 
			 
		} catch (Exception e) {
			
			System.out.println("Exception while taking Screesnhot"+e.getMessage());
		}
		return screenshotName;

		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
