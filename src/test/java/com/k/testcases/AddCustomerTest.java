package com.k.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.k.base.TestBase;
import com.k.utilities.TestUtil;

public class AddCustomerTest extends TestBase{
	
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String runmode) {
		
		if(!runmode.equalsIgnoreCase("Y"))
		{
			throw new SkipException("Skipping the testcase as Runmode is No");
		}
	
		click("addCustBtn_CSS");
		//type("firstName_CSS",data.get("firstName"));
		type("firstName_CSS",firstName);
		//type("lastName_CSS",data.get("lastName"));
		type("lastName_CSS",lastName);
		//type("postCode_CSS",data.get("postCode"));
		type("postCode_CSS",postCode);
		click("addCustSubBtn_CSS");
		Alert alert= wait.until(ExpectedConditions.alertIsPresent());
		//driver.switchTo().alert();
		alert.accept();
		
		
		
		
	}

	
}
