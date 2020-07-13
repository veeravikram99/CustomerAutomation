package com.cp.AllProductPageJourney;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.testng.Assert;

import com.cp.Base.BaseClass;
import com.cp.Base.PageElementsInterface;
import com.relevantcodes.extentreports.LogStatus;

public class CareJourney extends BaseClass implements PageElementsInterface{

	public static String[][] TestCaseData=BaseClass.ReadExcel("CareQuotation");
	public static String[][] FamilyData=BaseClass.ReadExcel("CareFamilyDetails");
	
	public static void GetQuote() throws Exception {
		
		String mnumber = TestCaseData[1][1].toString().trim();
		int size = mnumber.length();
		System.out.println("mobile number is: " + mnumber);
		String format = "^[789]\\d{9}$";
	
		if (mnumber.matches(format) && size == 10) {
			try {
			EnterText(By.xpath(Mobilenumber_xpath), mnumber);
			logger.log(LogStatus.PASS,"Mobile number is verified and Entered Mobile number is  "+mnumber );
			}catch(Exception e) {
				System.out.println("Still page is getting loading");
			}
		} else {
			System.out.println(" Not a valid mobile  Number");
		}
	
	String email = TestCaseData[1][2].toString().trim();
	System.out.println("Email is :" + email);
	Thread.sleep(5000);
	if (email.contains("@")) {
		EnterText(By.xpath(EmailID_xpath), email);
	} else {
		System.out.println("Not a valid email");
	}
	logger.log(LogStatus.PASS,"For proper format Email id is verified and Entered Email id is  "+email );
try {
	clickElement(By.xpath(GetQuoteButton_xpath));
	
	/*driver.findElement(By.xpath(EmailID_xpath)).clear();
	//String email = TestCaseData[1][2].toString().trim();
	System.out.println("Email is :" + email);
	Thread.sleep(5000);
	if (email.contains("@")) {
		EnterText(By.xpath(EmailID_xpath), email);
	} else {
		System.out.println("Not a valid email");
	}*/
	logger.log(LogStatus.PASS,"For proper format Email id is verified and Entered Email id is  "+email );
	String Yourname=TestCaseData[1][3].toString().trim();
	EnterText(By.xpath(yourname_xpath), Yourname);
	logger.log(LogStatus.PASS,"Your name is  "+Yourname );
	System.out.println("Your name is  "+Yourname);
}catch(Exception e) {
	System.out.println("Unable to select");
}
	String Pincode=TestCaseData[1][4].toString().trim();
	EnterText(By.xpath(Pincode_xpath), Pincode);
	logger.log(LogStatus.PASS,"Pincode is  "+Pincode );
	System.out.println("Pincode is  "+Pincode);
	Thread.sleep(5000);
	clickElement(By.xpath(NextButtonQuotation_xpath));
	String Gender=TestCaseData[1][5].toString().trim();
	Thread.sleep(8000);
	if(Gender.equalsIgnoreCase("male")) {
		clickElement(By.xpath(Malebutton_xpath));
	}else {
		clickElement(By.xpath(femalebutton_xpath));
	}
	logger.log(LogStatus.PASS,"Selected Gender is  "+Gender );
	System.out.println("Selected Gender is  "+Gender);
	}
	public static void SelectingInsureDetails() throws InterruptedException {
		String relationage=FamilyData[1][0].toString().trim();
		
		List<WebElement> dropdown=driver.findElements(By.xpath("//*[@class='form_input borderdinput age_input adult filled']/span"));
		//List<WebElement>dropdownvalue=driver.findElements(By.xpath("//*[@class='age_dropdown custom_dropdown']"));
		System.out.println("Size is "+dropdown.size());
		//int dpsizevalue=dropdownvalue.size();
		Thread.sleep(5000);
		for(WebElement DropDownName:dropdown) {
			System.out.println("Drop down value is :"+DropDownName.getText());
			if(DropDownName.getText().equals("Self")) {
				clickElement(By.xpath("//div[@class='panel_cont_inner dropdown_up']//div[1]//div[1]"));
				List<WebElement> dpvalues=driver.findElements(By.xpath("//div[@class='form_input borderdinput age_input adult filled in']//li[@class='drop_element']"));
				for(WebElement agegroupvalue:dpvalues) {
					
					if(agegroupvalue.getText().equals(relationage)) {
						clickElement(By.xpath("//div[@class='form_input borderdinput age_input adult filled in']//li[@class='drop_element'][contains(text(),'"+relationage+"')]"));
					break;
					}
				}
				
				break;
			}
			
		}
		System.out.println("Selected age is :"+relationage);
		Thread.sleep(3000);
		try {
		clickElement(By.xpath(relationsubmitbutton_xpath));
		}catch(Exception e){
			System.out.println("Unable to click on Relation submit button");
		}
		//Need to write code for more than one member
	}
	public static void PreExistingConditions() throws InterruptedException {
		String Pedcase=TestCaseData[1][6].toString().trim();
		Thread.sleep(6000);
		System.out.println("Slected ped is :"+Pedcase);
		if(Pedcase.equalsIgnoreCase("Yes")) {
			clickElement(By.xpath(PEDyes_xpath));
		}else {
			clickElement(By.xpath(PEDNo_xpath));
		}
	clickElement(By.xpath(PedenableCare_button_xpath));
	
	}
	public static void coverageTermAddons() throws InterruptedException {

		String Coverage=TestCaseData[1][7].toString().trim();
		/*List<WebElement> drag=driver.findElements(By.xpath("slider-range ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content ui-slider-pips"));
		int size=drag.size();
		System.out.println("Size is :"+size);*/
		Thread.sleep(5000);
		try {
		clickElement(By.xpath("//div[@class='slider-range ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content ui-slider-pips']/span[contains(text(),'"+Coverage+"')]"));
		logger.log(LogStatus.PASS,"Selected coverage is  "+Coverage );
		System.out.println("Selected coverage is :"+Coverage);
		}catch(Exception e) {
			System.out.println("UNable to select the coverage due to :"+e.getMessage());

		}
		String Term=TestCaseData[1][8].toString().trim();
		try {
		clickElement(By.xpath("//label[contains(text(),'"+Term+"')]"));
		logger.log(LogStatus.PASS,"Selected policy term  is  "+Term );
		}catch(Exception e) {
			System.out.println("Unable to select the term due to :"+e.getMessage());
		}
		String Addon1=TestCaseData[1][9].toString().trim();
		String Addon2=TestCaseData[1][10].toString().trim();
		String Addon3=TestCaseData[1][11].toString().trim();
		if(Addon1.equalsIgnoreCase("Yes")) {
			clickElement(By.xpath("//button[@id='UR_toInclude']"));
				}else {
					System.out.println("Not required to select UR");
		}
		if(Addon2.equalsIgnoreCase("Yes")) {
			clickElement(By.xpath("//button[@id='DA_toInclude']"));
				}else {
					System.out.println("Not required to select DA");
		}
		if(Addon3.equalsIgnoreCase("Yes")) {
			clickElement(By.xpath("//button[@id='AAC_toInclude']"));
				}else {
					System.out.println("Not required to select AAC");
		}
	}
	public static void PremiumAssertioncalculation() throws InterruptedException {
		scrollup();
		scrollup(); 
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
		WebElement quotepremi=driver.findElement(By.xpath("//span[@class='total_premium prem_amount care_premium']"));
		Quotationpremium_value=quotepremi.getText();
		System.out.println("Quotation premium for Care is :"+Quotationpremium_value);
		logger.log(LogStatus.PASS,"Quotation page premium   is  "+Quotationpremium_value );
		scrollup();
		clickElement(By.xpath("//p[contains(text(),'BUY NOW')]"));
		Thread.sleep(5000);
		try {
		String ProposerDetails_premium=driver.findElement(By.xpath("//span[@class='premium_total total_premium_top']")).getText();
		System.out.println("preium is:"+ProposerDetails_premium);
		ProposerDetails_premiumvalue=ProposerDetails_premium.replaceAll(",", "");
		
		/*String premium=ProposerDetails_premium.replaceAll(",", ""));
		System.out.println("Premium is :"+premium);*/
		}catch(Exception e) {
			System.out.println("Taking time to navigate to the next page");
		}
		System.out.println("proposer details page premium is :"+ProposerDetails_premiumvalue);
		logger.log(LogStatus.PASS,"proposer details page premium   is  "+ProposerDetails_premiumvalue );
		
		try {
			Assert.assertEquals(Quotationpremium_value, ProposerDetails_premiumvalue);
			logger.log(LogStatus.INFO,
					"Quotaion Premium and Proposer detailspage Premium is Verified and Both are Same : "+ ProposerDetails_premiumvalue.substring(1, ProposerDetails_premiumvalue.length()));
		} catch (AssertionError e) {
			System.out.println(Quotationpremium_value + " - failed");
			logger.log(LogStatus.FAIL, "Quotaion Premium and Proposal Premium are not Same");
			
		}
	}
	public static void ProposerDetails() throws InterruptedException {
		String Lname=TestCaseData[1][12].toString().trim();	
		EnterText(By.xpath(Lastname_xpath), Lname);
		 
		
		String Dob = TestCaseData[1][13].toString().trim();		
		try{
			Thread.sleep(2000);
				clickElement(By.xpath(calenderId_xpath));
				Thread.sleep(2000);
				String spilitter[]=Dob.split(",");			
				String eday = spilitter[0];
				String emonth = spilitter[1];
				String eYear = spilitter[2];
				Thread.sleep(3000);
				clickElement(By.xpath(monthdp_xpath));
				clickElement(By.xpath("//option[contains(text(),'"+emonth+"')]"));
				Thread.sleep(3000);
				clickElement(By.xpath("//select[@class='ui-datepicker-year']"));
				clickElement(By.xpath("//option[contains(text(),'"+eYear+"')]"));
				Thread.sleep(3000);
				clickElement(By.xpath("//div[@id='ui-datepicker-div']//tr/td[@data-handler='selectDay']/a[contains(text(),'"+eday+"')]"));
			
				}
				catch(Exception e)
				{
					System.out.println("Unable to Enter Start Date.");
				}
		/*if(PoicyType.equalsIgnoreCase("Regression")) {
		clickElement(By.xpath("//button[@class='btn_comon green_btn quikpayProposerDetailsForm']"));
		Thread.sleep(5000);
		//PaymentMode();
		}else {
			clickElement(By.xpath("//a[@class='link_brown']"));
		}*/
	}
	public static void AddressNominee() throws InterruptedException {
		String Addressl1=TestCaseData[1][14].toString().trim();
		Thread.sleep(5000);
		EnterText(By.xpath(addressline1_xpath), Addressl1);
		logger.log(LogStatus.PASS,"Address1 is  "+Addressl1 );
		System.out.println("Address1 is  "+Addressl1);
		String Addressl12=TestCaseData[1][15].toString().trim();
		EnterText(By.xpath(addressline2_xpath), Addressl12);
		logger.log(LogStatus.PASS,"Address2 is  "+Addressl12 );
		System.out.println("Address2 is  "+Addressl12);
		String landmark=TestCaseData[1][16].toString().trim();
		EnterText(By.xpath(Landmark_xpath), landmark);
		logger.log(LogStatus.PASS,"Landmark is  "+landmark );
		System.out.println("Landmark is  "+landmark);
	
		String NomineeName=TestCaseData[1][17].toString().trim();
		EnterText(By.xpath(NomineeName_xpath), NomineeName);
		logger.log(LogStatus.PASS,"Nominee name is  "+NomineeName );
		System.out.println("Nominee name is  "+NomineeName);
		String NomineeRelation=TestCaseData[1][18].toString().trim();
		Thread.sleep(4000);
		scrolldown();
		clickElement(By.xpath("//div[@class='relation_dropdown custom_dropdown nominee_relation']"));
		
		
		List<WebElement> rellist=driver.findElements(By.xpath("//ul[@class='custom_drop_cont']/li"));
		for(WebElement relationdata:rellist){
		
			if(relationdata.getText().equalsIgnoreCase(NomineeRelation)) {
				Thread.sleep(2000);
				clickElement(By.xpath("//ul[@class='custom_drop_cont']/li[contains(text(),'"+NomineeRelation+"')]"));
			}
		}
		
		clickElement(By.xpath(proposerdetailsPageNextbutton_xpath));
	}
	public static void InsuredDetails() throws InterruptedException {
		String[][] FamilyData = BaseClass.ReadExcel("CareFamilyDetails");
		Thread.sleep(3000);
		scrollup();
		String Membersdetails=TestCaseData[1][19].toString().trim();
		BaseClass.membres = Membersdetails.split("");
    int mcount;
    System.out.println("Length is :"+BaseClass.membres.length);
		for (int i = 0; i <= BaseClass.membres.length - 1; i++) {
			mcount = Integer.parseInt(BaseClass.membres[i].toString());
			if (i == 0) {
				
				// Select Self Primary
				EnterText(By.name("insured_members[self][height_feet]"), FamilyData[mcount][6].toString().trim());
				EnterText(By.name("insured_members[self][height_inches]"), FamilyData[mcount][7].toString().trim());
				EnterText(By.name("insured_members[self][weight]"), FamilyData[mcount][8].toString().trim());
			} else {

				// String firstName= "fname"+i+"_xpath";
				String Date = FamilyData[mcount][5].toString().trim();

				BaseClass.selecttext("ValidRelation" + i, FamilyData[mcount][1].toString().trim());
				// title
				BaseClass.selecttext("ValidRelTitle" + i, FamilyData[mcount][2].toString().trim());
				EnterText(By.name("RelFName" + i), FamilyData[mcount][3].toString().trim());
				EnterText(By.name("RelLName" + i), FamilyData[mcount][4].toString().trim());
				clickElement(By.name("rel_dob" + i));
				EnterText(By.name("rel_dob" + i), String.valueOf(Date));

				BaseClass.selecttext("relHeightFeet" + i, FamilyData[mcount][6].toString().trim());
				BaseClass.selecttext("relHeightInches" + i, FamilyData[mcount][7].toString().trim());
				EnterText(By.name("relWeight" + i), FamilyData[mcount][8].toString().trim());

			}
	}
		scrolldown();
		clickElement(By.xpath(insuredDetailsNext_xpath));
	}
	public static void HealthQuestionariesDetails() throws InterruptedException {
		String[][] QuestionSetData = BaseClass.ReadExcel("CareQuotation");
		String question1 = TestCaseData[1][20].toString().trim();
		//Question 1 
		if(question1.equalsIgnoreCase("YES")) {
			System.out.println("Need to write script for YES case");
		}else {
			try {
				Thread.sleep(1000);
			clickElement(By.xpath(" //div[@class='questionnaire_cont remove_border']//div[@class='qution_button']//span[contains(text(),'No')]"));
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			}
		
		
		//Question2
		String question2 = TestCaseData[1][20].toString().trim();
		if(question2.equalsIgnoreCase("YES")) {
			System.out.println("Need to write script for YES case");
		}else {
			clickElement(By.xpath(" //form[@id='proposal-details']//div[2]//div[2]//div[1]//div[3]//div[1]//div[2]//span[contains(text(),'"+question2+"')]"));
		}
		
		//question3
		String question3 = TestCaseData[1][20].toString().trim();
		if(question3.equalsIgnoreCase("YES")) {
			System.out.println("Need to write script for YES case");
		}else {
			clickElement(By.xpath("//*[@id=\"health_questionnaire\"]/section/div[2]/div[3]/div[1]/div[3]/div/div/span[contains(text(),'"+question3+"')]"));
		}
	}
}