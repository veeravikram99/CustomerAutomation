package com.cp.Base;

public interface PageElementsInterface {

public String HealthInsurance_xpath="//a[@class='drop_arrow nav_heading on_click_backarrow'][contains(text(),'Health Insurance')]";
public String CareProduct_xpath="//p[contains(text(),'Comprehensive Family Health Insurance Plan')]";
public String Mobilenumber_xpath="//form[@id='quote-form-step-1']//input[@id='mobile_no']";
public String EmailID_xpath="//form[@id='quote-form-step-1']//input[@id='email_id']";
public String GetQuoteButton_xpath="//button[@id='getQuote']";
public String yourname_xpath="//div[@class='floating-label']//input[@id='first_name']";
public String Pincode_xpath="//input[@id='pin_code']";
public String NextButtonQuotation_xpath="//button[@class='button_enable care_buttom_enable']";
public String Malebutton_xpath="//input[@id='male']";
public String femalebutton_xpath="//input[@id='female']";
public String relationsubmitbutton_xpath="//button[@class='relation_btn care_relation_btn']";
public String PEDyes_xpath="//span[contains(text(),'Yes')]";
public String PEDNo_xpath="//span[@class='map_view'][contains(text(),'No')]"; 
public String PedenableCare_button_xpath="//button[@class='ped_enable care_ped_enable']";

public String Lastname_xpath="//input[@id='last_name']";
public String calenderId_xpath="//input[@id='dob']";
public String monthdp_xpath="//select[@class='ui-datepicker-month']";

public String addressline1_xpath="//*[@name='addressLine1']";
public String addressline2_xpath="//*[@name='addressLine2']";
public String Landmark_xpath="//*[@name='landmark']";
public String NomineeName_xpath="//*[@name='nominee_name']";
public String proposerdetailsPageNextbutton_xpath="//button[@class='btn_comon green_btn proposerDetailsForm']";
public String insuredDetailsNext_xpath="//button[@class='btn_comon green_btn createPolicySubmit insuredDetailsForm']";

















}
