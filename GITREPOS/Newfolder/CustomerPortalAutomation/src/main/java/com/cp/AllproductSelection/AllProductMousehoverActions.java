package com.cp.AllproductSelection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.cp.Base.BaseClass;
import com.cp.Base.PageElementsInterface;
import com.relevantcodes.extentreports.LogStatus;

public class AllProductMousehoverActions extends BaseClass implements PageElementsInterface{

	
	public static void CareJourneySelect() throws InterruptedException {		
			
		try{
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath(HealthInsurance_xpath));
		action.moveToElement(we).moveToElement(driver.findElement(By.xpath(CareProduct_xpath))).click().build().perform();
	}catch(Exception e){
		logger.log(LogStatus.FAIL, "Unable to Click on  Care from health insurance module");
		
	}
	}
}
