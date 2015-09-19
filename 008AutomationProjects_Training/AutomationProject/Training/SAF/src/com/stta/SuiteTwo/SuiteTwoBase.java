package com.stta.SuiteTwo;

import java.io.IOException;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import com.stta.TestSuiteBase.SuiteBase;
import com.stta.utility.Read_XLS;
import com.stta.utility.SuiteUtility;

public class SuiteTwoBase extends SuiteBase{
	
	Read_XLS FilePath = null;
	String SheetName = null;
	String SuiteName = null;
	String ToRunColumnName = null;	
	
	//This function will be executed before SuiteTwo's test cases to check SuiteToRun flag.
	@BeforeSuite
	public void checkSuiteToRun() throws IOException{
		//Called init() function from SuiteBase class to Initialize .xls Files
		init();	
		//To set TestSuiteList.xls file's path In FilePath Variable.
		FilePath = TestSuiteListExcel;
		SheetName = "SuitesList";
		SuiteName = "SuiteTwo";
		ToRunColumnName = "SuiteToRun";
		
		//If SuiteToRun !== "y" then SuiteTwo will be skipped from execution.
		if(!SuiteUtility.checkToRunUtility(FilePath, SheetName,ToRunColumnName,SuiteName)){			
			//It will throw SkipException to skip test suite's execution and suite will be marked as skipped In testng report.
			throw new SkipException(SuiteName+"'s SuiteToRun Flag Is 'N' Or Blank. So Skipping Execution Of "+SuiteName);
		}		
	}	
}
