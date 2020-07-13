package com.cp.MainTestClass;



import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.cp.AllProductPageJourney.CareJourney;
import com.cp.AllproductSelection.AllProductMousehoverActions;
import com.cp.Base.BaseClass;
import com.cp.utility.ReadExcel;
import com.relevantcodes.extentreports.LogStatus;

public class RunnableTestClass extends BaseClass{

	@Test
	public static void CareproductQuotationPage() throws Exception {
		
		String[][] TestCase=BaseClass.ReadExcel("Care_Testcase");
		String TestcaseName=TestCase[1][1].toString().trim();
		ReadExcel fis = new ReadExcel(System.getProperty("user.dir") + "\\TestData\\CPFramework.xlsx");
		int rowCount = fis.getRowCount("Care_Testcase");
		System.out.println("Total Number of Row in Sheet : "+rowCount);
		
		try {
			String TestCaseName = (TestCase[1][0].toString().trim() +" - " +TestCase[1][1].toString().trim());
			logger = extent.startTest(TestCaseName);
			System.out.println("customer portal care product test case name is -" +TestCaseName);
			
		LaunchBrowser();
		Thread.sleep(5000);
		AllProductMousehoverActions.CareJourneySelect();
		CareJourney.GetQuote();
		CareJourney.SelectingInsureDetails();
		CareJourney.PreExistingConditions();
		CareJourney.coverageTermAddons();
		CareJourney.PremiumAssertioncalculation();
		PoicyType = carddetails[1][5].toString().trim();
		if(PoicyType.equalsIgnoreCase("Regression")) {
			CareJourney.ProposerDetails();
			clickElement(By.xpath("//button[@class='btn_comon green_btn quikpayProposerDetailsForm']"));
			Thread.sleep(5000);
			PaymentMode();
			Thread.sleep(5000);
			AxisSimulatorOTP();
			
			}else {
				try {
					scrolldown();
				clickElement(By.xpath("//*[@class='text-center']/a[contains(text(),'Continue with standard payment')]"));
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				CareJourney.ProposerDetails();
				CareJourney.AddressNominee();
				CareJourney.InsuredDetails();
				CareJourney.HealthQuestionariesDetails();
			}
		
		BaseClass.setCellData("Care_Testcase", "PASS", 1, 3);
		logger.log(LogStatus.PASS, logger.addScreenCapture(BaseClass.getScreenhot(driver, "PASS")));
		
	}catch(Exception e) {
		BaseClass.setCellData("Care_Testcase", "Fail", 1, 3);
		System.out.println(e.getMessage());
		logger.log(LogStatus.FAIL, logger.addScreenCapture(BaseClass.getScreenhot(driver, "Failure")));
		logger.log(LogStatus.FAIL, e.getMessage());
		logger.log(LogStatus.FAIL, "Test Case is Failed");
		Thread.sleep(4000);
		// driver.quit();

	}
	}
}
