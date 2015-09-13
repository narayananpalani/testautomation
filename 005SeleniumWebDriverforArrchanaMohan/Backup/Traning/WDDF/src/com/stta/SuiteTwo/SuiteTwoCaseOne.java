//Find More Tutorials On WebDriver at -> http://software-testing-tutorials-automation.blogspot.com
package com.stta.SuiteTwo;

import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.stta.utility.Read_XLS;
import com.stta.utility.SuiteUtility;

//SuiteTwoCaseOne Class Inherits From SuiteTwoBase Class.
//So, SuiteTwoCaseOne Class Is Child Class Of SuiteTwoBase Class And SuiteBase Class.
public class SuiteTwoCaseOne extends SuiteTwoBase{
	Read_XLS FilePath = null;	
	String SheetName = null;
	String TestCaseName = null;	
	String ToRunColumnNameTestCase = null;
	String ToRunColumnNameTestData = null;
	String TestDataToRun[]=null;
	static boolean TestCasePass=true;
	static int DataSet=-1;	
	static boolean Testskip=false;
	static boolean Testfail=false;
	SoftAssert s_assert =null;
	
	@BeforeTest
	public void checkCaseToRun() throws IOException{
		//Called init() function from SuiteBase class to Initialize .xls Files
		init();	
		//To set SuiteTwo.xls file's path In FilePath Variable.
		FilePath = TestCaseListExcelTwo;		
		TestCaseName = this.getClass().getSimpleName();
		//SheetName to check CaseToRun flag against test case.
		SheetName = "TestCasesList";
		//Name of column In TestCasesList Excel sheet.
		ToRunColumnNameTestCase = "CaseToRun";
		//Name of column In Test Case Data sheets.
		ToRunColumnNameTestData = "DataToRun";
		
		//To check test case's CaseToRun = Y or N In related excel sheet.
		//If CaseToRun = N or blank, Test case will skip execution. Else It will be executed.
		if(!SuiteUtility.checkToRunUtility(FilePath, SheetName,ToRunColumnNameTestCase,TestCaseName)){			
			//To report result as skip for test cases In TestCasesList sheet.
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "SKIP");
			//To throw skip exception for this test case.
			throw new SkipException(TestCaseName+"'s CaseToRun Flag Is 'N' Or Blank. So Skipping Execution Of "+TestCaseName);
		}
		//To retrieve DataToRun flags of all data set lines from related test data sheet.
		TestDataToRun = SuiteUtility.checkToRunUtilityOfData(FilePath, TestCaseName, ToRunColumnNameTestData);
	}
	
	//Accepts 3 column's String data In every Iteration.
	@Test(dataProvider="SuiteTwoCaseOneData")
	public void SuiteTwoCaseOneTest(String DataCol1,String DataCol2,String ExpectedResult){
		
		DataSet++;
		
		//Created object of testng SoftAssert class.
		s_assert = new SoftAssert();		
		
		//If found DataToRun = "N" for data set then execution will be skipped for that data set.
		if(!TestDataToRun[DataSet].equalsIgnoreCase("Y")){
			//If DataToRun = "N", Set Testskip=true.
			Testskip=true;
			throw new SkipException("DataToRun for row number "+DataSet+" Is No Or Blank. So Skipping Its Execution.");
		}
		
		//If found DataToRun = "Y" for data set then bellow given lines will be executed.
		//To Convert data from String to Integer
		int ValueOne = Integer.parseInt(DataCol1);
		int ValueTwo = Integer.parseInt(DataCol2);		
		int ExpectedResultInt =  Integer.parseInt(ExpectedResult);
				
		//To Initialize Firefox browser.
		loadWebBrowser();
		
		driver.get(Param.getProperty("siteURL")+"/2014/04/calc.html");		
		getElementByName("txt_Result").clear();
		getElementByXPath("btn_Calc_PrePart",ValueOne,"btn_Calc_PostPart").click();
		getElementByID("btn_multiply").click();
		getElementByXPath("btn_Calc_PrePart",ValueTwo,"btn_Calc_PostPart").click();
		getElementByCSS("btn_Equals").click();
		String Result = getElementByName("txt_Result").getAttribute("value");
		int ActualResultInt =  Integer.parseInt(Result);
		if(!(ActualResultInt==ExpectedResultInt)){
			Testfail=true;	
			s_assert.assertEquals(ActualResultInt, ExpectedResultInt, "ActualResult Value "+ActualResultInt+" And ExpectedResult Value "+ExpectedResultInt+" Not Match");
		}
		
		if(Testfail){
			//At last, test data assertion failure will be reported In testNG reports and It will mark your test data, test case and test suite as fail.
			s_assert.assertAll();		
		}
	}
	
	//@AfterMethod method will be executed after execution of @Test method every time.
	@AfterMethod
	public void reporterDataResults(){		
		if(Testskip){
			//If found Testskip = true, Result will be reported as SKIP against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "SKIP");
		}	
		else if(Testfail){
			//To make object reference null after reporting In report.
			s_assert = null;
			//Set TestCasePass = false to report test case as fail In excel sheet.
			TestCasePass=false;
			//If found Testfail = true, Result will be reported as FAIL against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "FAIL");			
		}
		else{
			//If found Testskip = false and Testfail = false, Result will be reported as PASS against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "PASS");
		}
		//At last make both flags as false for next data set.
		Testskip=false;
		Testfail=false;
	}
	
	//This data provider method will return 3 column's data one by one In every Iteration.
	@DataProvider
	public Object[][] SuiteTwoCaseOneData(){
		//To retrieve data from Data 1 Column,Data 2 Column and Expected Result column of SuiteTwoCaseOne data Sheet.
		//Last two columns (DataToRun and Pass/Fail/Skip) are Ignored programatically when reading test data.
		return SuiteUtility.GetTestDataUtility(FilePath, TestCaseName);
	}

	//To report result as pass or fail for test cases In TestCasesList sheet.
	@AfterTest
	public void closeBrowser(){
		//To Close the web browser at the end of test.
		closeWebBrowser();
		if(TestCasePass){
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "PASS");
		}
		else{
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "FAIL");			
		}		
	}
}