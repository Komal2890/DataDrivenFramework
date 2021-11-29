package com.k.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.k.base.TestBase;
import com.k.utilities.TestUtil;

public class OpenAccountTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccountTest(String customerName,String currency, String alerttext) throws InterruptedException
	{
		
		click("openAccountBtn_CSS");
		//select("customer_CSS",data.get("customerName"));
		select("customer_CSS",customerName);
		//select("currency_CSS",data.get("currency"));
		select("currency_CSS",currency);
		click("process_CSS");
		Thread.sleep(2000);
		Alert a = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(a.getText().contains(alerttext));
		a.accept();
		//Assert.fail("Failing a testcase");
		
		
	}
	
	
}
