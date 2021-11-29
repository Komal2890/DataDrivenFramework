package com.k.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.k.utilities.ExcelReader;
import com.k.utilities.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

//	WebDriver--done
//	Properties--done
//	Logs--log4j jar, log-selenium,application log, log4j.properties file, logger class
//	ExtentReports--add dependency
//	DB
//	Excel
//	Mail
//	ReportNG--add dependency,add listener, add logs in testcase
//	Jenkins

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public static String Browser;
	public static ExtentHtmlReporter  htmlReporter;
	//public static ExtentReports extent;
	public static ExtentTest test;
	
	
	
	@BeforeSuite
	public void setUp() throws IOException {
		if (driver == null) {

			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			config.load(fis);

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);
		}
		
		if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
			Browser=System.getenv("browser");
		}else {
			Browser= config.getProperty("browser");
			}
		
		config.setProperty("browser", Browser);

		if (config.getProperty("browser").equals("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (config.getProperty("browser").equals("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (config.getProperty("browser").equals("ie")) {

			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();

		}
		log.debug("Setup done");
		driver.get(config.getProperty("testSiteURl"));
		log.debug("Navigated to:"+ config.getProperty("testSiteURl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		wait=new WebDriverWait(driver,5);
		

	}
	
	
	
	public boolean isElementTrue(By by) {
		
		try {
			driver.findElement(by);
			return true;
		}
		catch(NoSuchElementException e)
		{
			return false;
		}
	}
	
	public static void verifyEquals(String actual,String expected) throws IOException
	{
		try {
			
			Assert.assertEquals(actual, expected);
		}
		catch(Throwable t) {
			TestUtil.captureScreenshot();
			Reporter.log("</br>" + "Verification failure:"+ t.getMessage()+"</br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img> </a>");
			
			
		}
	}
	
	public void click(String locator)
	{
		if(locator.endsWith("_CSS"))
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		else if (locator.endsWith("_XPATH"))
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		else if (locator.endsWith("_ID"))
			driver.findElement(By.id(OR.getProperty(locator))).click();
		
		test.log(Status.INFO,"Clicking on:"+ locator);
	}
	
	public static WebElement dropdown;
	public void select(String locator, String value) {
		if(locator.endsWith("_CSS"))
			dropdown= driver.findElement(By.cssSelector(OR.getProperty(locator)));
		else if (locator.endsWith("_XPATH"))
			dropdown= driver.findElement(By.xpath(OR.getProperty(locator)));
		else if (locator.endsWith("_ID"))
			dropdown= driver.findElement(By.id(OR.getProperty(locator)));
		
		Select s= new Select(dropdown);
		s.selectByVisibleText(value);
		
		test.log(Status.INFO,"Selecting from dropdown:"+ locator + "value as" + value );
		
	}
	
	public void type(String locator,String value)
	{
		if(locator.endsWith("_CSS"))
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		else if (locator.endsWith("_XPATH"))
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		else if (locator.endsWith("_ID"))
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		
		test.log(Status.INFO,"Typing:"+ locator);
	}
	
	

	@AfterSuite
	public void testDown() {

		if(driver!=null) {
			driver.quit();
		}
		log.debug("Test execution completed");
		
		
	}
}
