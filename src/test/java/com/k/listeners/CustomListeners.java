package com.k.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.k.base.TestBase;
import com.k.utilities.ExtentManager;
import com.k.utilities.MonitoringMail;
import com.k.utilities.TestConfig;
import com.k.utilities.TestUtil;



public class CustomListeners extends TestBase implements ITestListener, ISuiteListener{

	
	public static String messageBody;
	static Date d = new Date();
	String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	ExtentReports extent = ExtentManager.getInstance(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+fileName);
	
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	

	
	public void onTestStart(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName()+"     @TestCase : "+result.getMethod().getMethodName());
        testReport.set(test);
		
		
	}
	

	public void onTestSuccess(ITestResult result) {
		//logger=extent.createTest(result.getName()); // create new entry in th report
		Markup m = MarkupHelper.createLabel(result.getName(),ExtentColor.GREEN);
		test.log(Status.PASS,m); // send the passed information to the report with GREEN color highlighted
		
	}

	public void onTestFailure(ITestResult result) {
		
		//logger=extent.createTest(result.getName()); // create new entry in the report
		Markup m = MarkupHelper.createLabel(result.getName(),ExtentColor.RED);
		test.log(Status.FAIL,m); // send the passed information to the report with GREEN color highlighted
		try {
			TestUtil.captureScreenshot();
			test.fail("Screenshot is below:" + test.addScreenCaptureFromPath(TestUtil.screenshotName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
		//ReportNG
		
		System.setProperty("org.uncommons.reportng.escape-output","false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.log("Capturing screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+" >Screenshot</a>");
		Reporter.log("</br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img> </a>");
		
		
	}

	public void onTestSkipped(ITestResult result) {
		//logger=extent.createTest(result.getName()); // create new entry in the report
		Markup m = MarkupHelper.createLabel(result.getName(),ExtentColor.YELLOW);
		test.log(Status.SKIP,m);
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		
		
	}

	public void onFinish(ITestContext context) {
		if (extent != null) {

			extent.flush();
		}
		
	}


	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onFinish(ISuite suite) {
		MonitoringMail mail = new MonitoringMail();
		
		try {
			messageBody = "https://"+ InetAddress.getLocalHost().getHostAddress()+ ":8080/job/DataDrivenLiveProject/Extent_20Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(messageBody);
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
