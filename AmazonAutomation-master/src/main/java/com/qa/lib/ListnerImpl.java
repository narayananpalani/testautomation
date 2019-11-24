package com.qa.lib;

import org.testng.ITestContext;
import org.testng.ITestListener;

import org.testng.ITestResult;




public class ListnerImpl implements ITestListener


{

	@Override
	public void onTestStart(ITestResult result)
	{
		System.out.println("===========================================================");
		System.out.println("Test Case Execution Started,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{
		System.out.println("===========================================================");
		System.out.println("Test Case Executed Sucesfully,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
	}

	@Override
	public void onTestFailure(ITestResult result) 
	{
		System.out.println("===========================================================");
		System.out.println("Test Case Execution Got Failed,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestSkipped(ITestResult result)
	{
		System.out.println("===========================================================");
		System.out.println("Test Case is Skipped,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
	
		
	}

	@Override
	public void onStart(ITestContext context)
	{
	
		
	}

	@Override
	public void onFinish(ITestContext context) 
	{
	
		
	}

	

}
