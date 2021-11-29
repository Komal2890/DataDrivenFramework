package com.k.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.k.base.TestBase;

public class ExtentManager extends TestBase {
	
	private static ExtentReports extent;
	
	public static ExtentReports getInstance(String fileName) {
	
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
	       
        
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name","localhost");
		extent.setSystemInfo("Environemnt","QA");
		extent.setSystemInfo("user","komal");
        
        
        return extent;
	}

}
