package com.k.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.k.base.TestBase;

public class BankManagerLoginTest extends TestBase{

	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException 
	{
		
		log.debug("Inside Login Test");
		click("bmlBtn_CSS");
		//Assert.assertTrue(isElementTrue(By.cssSelector(OR.getProperty("addCustBtn"))),"Login not successful");
		Thread.sleep(3000);
		log.debug("Login test completed successfully");
		//Assert.fail("Failing a testcase");		
		}
}
