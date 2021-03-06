package app.freecharge.listeners;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import app.freecharge.androiddriver.DriverInitialization;

import java.io.IOException;

public class TestListener extends TestListenerAdapter {

	private static ExtentReports report = new ExtentReports(System.getProperty("user.dir")+"\\test-output\\FreechargeAppTestResults.html");
	private static ExtentTest test;

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		super.onTestStart(result);
		System.out.println("Inside Test Start Method " + result.getMethod().getMethodName());
		test = report.startTest(result.getMethod().getMethodName());
		test.log(LogStatus.INFO, "Name: "+result.getName());
		//test.log(LogStatus.INFO, "TestName from TestClass : "+result.getTestClass().getTestName());
		//test.log(LogStatus.INFO, "Test Name: "+result.getTestName());
		//test.log(LogStatus.INFO, "Test Class Name: "+result.getTestClass().getName());
		test.assignCategory("FreechargeApp Tests");
		test.log(LogStatus.INFO, "Started Executing Test: "+ result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		String filename = tr.getMethod().getMethodName()+System.currentTimeMillis()+".jpg";
		super.onTestFailure(tr);
		System.out.println("Inside Test Failure Method " + tr.getMethod().getMethodName());
		test.log(LogStatus.ERROR, tr.getThrowable().getMessage());
		try {
			DriverInitialization.takescreenshot(filename);
			String imagepath = test.addScreenCapture(System.getProperty("user.dir")+"\\Screenshots\\"+filename);
			test.log(LogStatus.INFO, "Screenshot of the Page when this test failed.."+imagepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		report.endTest(test);
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		// TODO Auto-generated method stub
		String filename = tr.getMethod().getMethodName()+System.currentTimeMillis()+".jpg";
		super.onTestSuccess(tr);
		System.out.println("Inside Test Success Method " +tr.getMethod().getMethodName());
		test.log(LogStatus.PASS, tr.getName()+" succesfully passed");
		try {
			DriverInitialization.takescreenshot(filename);
			String imagepath = test.addScreenCapture(System.getProperty("user.dir")+"\\Screenshots\\"+filename);
			test.log(LogStatus.INFO, "Screenshot of the Page when this test succesfully passed.."+imagepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		report.endTest(test);
	}

	@Override
	public void onFinish(ITestContext testContext) {
		// TODO Auto-generated method stub
		super.onFinish(testContext);
		System.out.println("Finished Running all tests");
		test.log(LogStatus.INFO, "Finished Running all tests");
		report.flush();
		report.close();
	}

	public static ExtentTest getTest() {
		return test;
	}

}
