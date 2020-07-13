package com.cp.Base;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;




public class BaseClass {
	
	public static String[] membres = null;
	public static String PoicyType;
		 
    	private static XSSFSheet ExcelWSheet1;

    	private static XSSFWorkbook ExcelWBook1;

    	private static XSSFCell Cell1;

    	private static XSSFRow Row1;
public static WebDriver driver;
private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
private static XSSFCell Cell;
private static XSSFRow Row;

static String FilePath = System.getProperty("user.dir") + "\\TestData\\CPFramework.xlsx";

public static XSSFRow row = null;
public static XSSFCell cell = null;
public static File file = null;
public static FileInputStream fileInput = null;
public static int colCount = 0;

public static int rowCount;
public static String[] test;
public static ExtentReports report;
public static ExtentTest logger;
public static ExtentReports extent;
public static Properties Credential = null;
public static String Quotationpremium_value;
public static String ProposerDetails_premiumvalue;
public static String[][] carddetails=BaseClass.ReadExcel("Credentials");
public static void LaunchBrowser() throws IOException {
String[][] TestCaseData = ReadExcel("Credentials");
Properties credential = new Properties();
String path = System.getProperty("user.dir") + ".\\Config Files\\Credential.properties";
System.out.println("Path is : "+path);
FileInputStream ip = new FileInputStream(path);
credential.load(ip);
String Browser = TestCaseData[3][0].toString().trim();
Credential = new Properties();
FileInputStream ip1 = new FileInputStream(
		System.getProperty("user.dir") + "\\Config Files\\Credential.properties");
Credential.load(ip1);
//Verifying Browser from Credentials.properties file and Executing the same


if(credential.get("Browser").toString().equals("firefox"))
{
	driver=new FirefoxDriver();
}

else if (credential.get("Browser").toString().equals("chrome"))
{
	ChromeOptions options = new ChromeOptions();
	HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
	chromeOptionsMap.put("plugins.plugins_disabled", new String[] {
			"Chrome PDF Viewer"
	});
	chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
	
	String downloadFilepath = "D:\\DownloadforPDF";
	chromeOptionsMap.put("download.default_directory", downloadFilepath);
	System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	driver = new ChromeDriver(options);
	driver.manage().deleteAllCookies();
}
else if (credential.get("Browser").toString().equals("ie"))
{
	DesiredCapabilities ds = DesiredCapabilities.internetExplorer();
	ds.setCapability( InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
	driver = new InternetExplorerDriver();

}

driver.manage().window().maximize();
String Environment = TestCaseData[3][1].toString().trim();
if (Environment.contains("QC")) {

	openURL((String) Credential.get("BaseURLQC"));
} else if (Environment.contains("UAT")) {
	
	openURL((String) Credential.get("BaseURLUAT"));
} else if (Environment.contains("Staging")) {

	openURL((String) Credential.get("BaseURLStage"));

}

}
public static void openURL(String url) {
	driver.get(url);
	if (driver.getPageSource().contains("certificate")
			&& Credential.get("Browser").toString().equals("IE")) {
		driver.navigate().to("javascript:document.getElementById('overridelink').click()");
	}
}
@DataProvider(name="Login")
public static String[][] ReadExcel(String sheetname) {

	String[][] excelData = null;
		try {

			String FilePath=".\\TestData\\CPFramework.xlsx";
			
			FileInputStream finputStream = new FileInputStream(new File(FilePath));

			/*@SuppressWarnings("resource")*/
			XSSFWorkbook workbook = new XSSFWorkbook(finputStream);
			XSSFSheet sheet = workbook.getSheet(sheetname);

			 colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			 //System.out.println("Column count is : "+colCount);

			 rowCount = sheet.getPhysicalNumberOfRows();

			ArrayList<String> sheetNames = new ArrayList<String>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				sheetNames.add(workbook.getSheetName(i));
			}
			excelData = new String[rowCount][colCount];

			for (int Nrow = 0; Nrow < rowCount; Nrow++) {

				row = sheet.getRow(Nrow);

				for (int Ncolumn = 0; Ncolumn < colCount; Ncolumn++) {

					cell = sheet.getRow(Nrow).getCell(Ncolumn);

					DataFormatter df = new DataFormatter();
					excelData[Nrow][Ncolumn] = df.formatCellValue(cell);

				}

			}

		} catch (Exception e) {
			
		}
		
		return excelData;
		}

//Write Excel

	public static void setCellData1(String SheetName, String TestData, String Result,  int RowNum, int ColNum , int Colnum) throws Exception	
	{
	       try{
	    	   FileInputStream ExcelFile = new FileInputStream(FilePath);
	  		 
	   		// Access the required test data sheet
	   		 
	   		ExcelWBook = new XSSFWorkbook(ExcelFile);
	   		 
	   		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	 
	          	Row  = ExcelWSheet.getRow(RowNum);
	 
	//Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
	 
	
	Cell = Row.createCell(Colnum);
	Cell.setCellValue(TestData);
	 
	Cell = Row.createCell(ColNum);
	 
	Cell.setCellValue(Result);
	 

	          // Constant variables Test Data path and Test Data file name
	 
	          FileOutputStream fileOut = new FileOutputStream(FilePath);
	 
	          ExcelWBook.write(fileOut);
	 
	          fileOut.flush();
	 
	//fileOut.close();
	 
	}catch(Exception e){
	 
	throw (e);
	 
	}
	 
	}
	public static void setCellData(String SheetName, String Result,  int RowNum, int ColNum) throws Exception	{
		 
		
		 
		
	       try{
	    	   FileInputStream ExcelFile = new FileInputStream(FilePath);
	  		 
	   		// Access the required test data sheet
	   		 
	   		ExcelWBook = new XSSFWorkbook(ExcelFile);
	   		 
	   		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	 
	          	Row  = ExcelWSheet.getRow(RowNum);
	 
	//Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
	 
	/*if (Cell == null) {*/
	 
	Cell = Row.createCell(ColNum);
	 
	Cell.setCellValue(Result);
	 
	/*} else {*/
	 
	Cell.setCellValue(Result);
	 
	//}
	 
	          // Constant variables Test Data path and Test Data file name
	 
	          FileOutputStream fileOut = new FileOutputStream(FilePath);
	 
	          ExcelWBook.write(fileOut);
	 
	          fileOut.flush();
	 
	//fileOut.close();
	 
	}catch(Exception e){
	 
	throw (e);
	 
	}
	 
	}
	
     	
     	

 	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

 	public static void setExcelFile(String Path,String SheetName) throws Exception {

    try {

        // Open the Excel file

FileInputStream ExcelFile = new FileInputStream(FilePath);

//Access the required test data sheet

ExcelWBook1 = new XSSFWorkbook(ExcelFile);

ExcelWSheet1 = ExcelWBook1.getSheet(SheetName);

} catch (Exception e){

throw (e);

}

}
@BeforeTest
public static void Report(){
	extent = new ExtentReports(System.getProperty("user.dir") + "/Reports/TestReport.html", true);
	
	extent.addSystemInfo("Host Name", "UAT").addSystemInfo("Environment", "Customer Portal UAT")
	.addSystemInfo("User Name", "Bijaya Kumar Sahoo");
	//extent.loadConfig(new File("E:\\MobileAutomationPOC\\ReloigareMobilePOC\\extent-config.xml"));
	extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

}

//This method is to capture the screenshot and return the path of the screenshot.

	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception 
	{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static void mousehover(By by) {
		//waitForElement(by);
		Actions action=new Actions(driver);
		action.moveToElement(driver.findElement(by)).perform();

	}
	@AfterMethod(alwaysRun = true)
	public static void getResult(ITestResult result) throws Exception {
		try {
		if (result.getStatus() == ITestResult.FAILURE) {

			if (result.getStatus() == ITestResult.FAILURE) {
				logger.log(LogStatus.FAIL, "Test Case Failed method name is  " + result.getName());
		
				String screenshotPath = BaseClass.getScreenhot(driver, result.getName());
				//To add it in the extent report 
				logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
			}

		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case  is Skipped" + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			//logger.log(LogStatus.PASS, "Test Case Pass is " + result.getName());
		}
		}catch(Exception t){
			 logger.log(LogStatus.ERROR,t.fillInStackTrace());
		}

	

	}
	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
	public static void waitForElement(By by) {

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

	}
	public static String clickElement(By by) throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(by).click();
		return null;

	}
	public static void EnterText(By by,String string) throws InterruptedException {
		
		Thread.sleep(3000);
		driver.findElement(by).sendKeys(string);
	}
	public static void ExplicitWait(By by, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	public static void ImplicitWait(int Time) {
		driver.manage().timeouts().implicitlyWait(Time, TimeUnit.SECONDS);
	}
	public static void Fluentwait1(By by){

		Wait<WebDriver> wait2 = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).withMessage("CP Url is not loaded so unable to find Userid and Password Field.").ignoring(NoSuchElementException.class);

		wait2.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	public static void scrollup()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
		/*System.out.println("Scroll Up is Done");*/

	}

	public static void scrolldown(){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
		/*System.out.println("Scroll Down is Done");*/
	}
	public static void AxisSimulatorOTP() throws InterruptedException {
		String  title = driver.getTitle();
		System.out.println("Title og Axis emulator page is :  "+title);
		if(title.equals("PayUbiz")||title.equals("Axis Simulator")) {
		EnterText(By.xpath("//input[@name='password']"), "123456");
		clickElement(By.xpath("//input[@id='submitBtn']"));
		}else {
			System.out.println("Page is not available");
		}
	}
	//Select Option Values using name
	public static void selecttext(String selectName, String selectText) throws InterruptedException {
		WebElement mySelectElement = driver.findElement(By.name(selectName));
		Select titleDropdown= new Select(mySelectElement);
		Thread.sleep(2000);
		//titleDropdown.selectByValue(Title.toString());
		
		titleDropdown.selectByVisibleText(selectText.toString());
		System.out.println("Selected Tilte is :"+ selectText.toString());

	}
public static void PaymentMode() throws InterruptedException {
		/*System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();*/
	String CardNumber = carddetails[1][0].toString().trim();
	String name = carddetails[1][1].toString().trim();
	String month = carddetails[1][2].toString().trim();
	String year = carddetails[1][4].toString().trim();
	String cvv = carddetails[1][3].toString().trim();
	Thread.sleep(10000);
	try{
    System.out.println("Entered Credit Card Number : "+CardNumber);
		
		Screen screen=new Screen();
		Pattern cardnumberimg=new Pattern(".\\PaymentScreenshot\\Capture1.PNG");
		Pattern Cardnameimg=new Pattern(".\\PaymentScreenshot\\Capture2.PNG");
		Pattern monthimg=new Pattern(".\\PaymentScreenshot\\Capture3.PNG");
		Pattern yearimg=new Pattern(".\\PaymentScreenshot\\Capture4.PNG");
		Pattern cvvimg=new Pattern(".\\PaymentScreenshot\\Capture5.PNG");
		
		
            Thread.sleep(5000);
          screen.type(cardnumberimg, CardNumber);
           screen.type(Cardnameimg,name);
         screen.type(monthimg,month);
          Thread.sleep(3000);
          screen.type(yearimg,year);
          Thread.sleep(3000);
           screen.type(cvvimg,cvv);
      clickElement(By.xpath("//div[@id='CreditCard']//button[@id='common_pay_btn']"));
		/*System.out.println("Entered Credit Card Number : "+CardNumber);
		
		Screen screen=new Screen();
		Pattern cardnumberimg=new Pattern("C:\\Users\\bijaya\\Desktop\\PaymentScreenshot\\Capture1.PNG");
		Pattern Cardnameimg=new Pattern("C:\\Users\\bijaya\\Desktop\\PaymentScreenshot\\Capture2.PNG");
		Pattern monthimg=new Pattern("C:\\Users\\bijaya\\Desktop\\PaymentScreenshot\\Capture3.PNG");
		Pattern yearimg=new Pattern("C:\\Users\\bijaya\\Desktop\\PaymentScreenshot\\Capture4.PNG");
		Pattern cvvimg=new Pattern("C:\\Users\\bijaya\\Desktop\\PaymentScreenshot\\Capture5.PNG");
		
		driver.get("https://rhicluat.religarehealthinsurance.com/payment/stype/online/hash/e37c4166bcfe0796d560933f3055c49cc441573f29b27c8455c4261023de0f356a1c30441236fa908223e7bac3ae28b53681016c6decc2a7cde0bebbb1a2c0d6");
Thread.sleep(5000);
screen.type(cardnumberimg, CardNumber);
screen.type(Cardnameimg,name);
screen.type(monthimg,month);
Thread.sleep(3000);
screen.type(yearimg,year);
Thread.sleep(3000);
screen.type(cvvimg,cvv);
clickElement(By.xpath("//div[@id='CreditCard']//button[@id='common_pay_btn']"));*/
		
/*driver.get("https://rhicluat.religarehealthinsurance.com/payment/stype/online/hash/8f92adcce2b5a7a036516216cec911efa19d29151cf713c5f24591d60de747dcb64b046ffb329272eed19204ef3e37e04f49ad7bfdd33a9270441c1f867f0fe9");
		List<WebElement> iframes=driver.findElements(By.tagName("iframe"));
		System.out.println(iframes.size());
		
		WebElement e1=driver.findElement(By.xpath("//div[@id='CreditCard']//div[@class='card_number_div']//iframe"));
		//driver.switchTo().frame(e1);
		
		System.out.println(e1.getAttribute("name"));
		String str=e1.getAttribute("name");
		driver.switchTo().frame(str);
		Screen s=new Screen();
		s.click(driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]")));
		*/
		/*WebElement e2=driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]"));
		System.out.println(e2.getAttribute("name"));*/
		
		//WebElement element = driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]"));
	/*for(int i=0; i<=iframes.size(); i++) {
		try {
			driver.switchTo().frame(i);
			//driver.navigate().refresh();
			//iframes.get(i).click();
			//System.out.println(iframes.get(i).getAttribute("name"));
			WebElement e2=driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]"));
			System.out.println(e2.getAttribute("name"));
		//WebElement e1=(WebElement)((JavascriptExecutor)driver).executeScript("arguments[0].removeAttribute('style')", driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]")));
	    //System.out.println(e2);
		}
	    catch(Exception e) {
	    	
	    }
	}*/
		//element.sendKeys("1234");
		
		/*JavascriptExecutor  jse=(JavascriptExecutor)driver;
		WebElement el=(WebElement)jse.executeScript("return arguments[0].text", driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]")));
		
		el.getAttribute("name");*/
		
		/*JavascriptExecutor jse = (JavascriptExecutor)driver;
		 jse.executeScript("arguments[0].value='12345';", driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]")));*/
		
		/*for(int i=0; i<=3; i++) {
			try {
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("document.getElementById('card_number').value='1611';");
			}
			catch(Exception e) {
				
			}
		}*/
		
		/*for(int i=0; i<=iframes.size(); i++) {
			try {
				System.out.println(i);
				driver.switchTo().frame(i);
				
				List<WebElement> list=driver.findElements(By.xpath("//input[@id='card_number']"));
				
				for(int j=0; j<=list.size(); j++) {
					int x=list.get(j).getLocation().getX();
					if(x!=0) {
						list.get(i).sendKeys("123455");
					}
				}
				
				//WebElement e2=driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]"));
				   Actions a=new Actions(driver);
				   a.moveToElement(e2).click().sendKeys("12344").perform();
			}
			catch(Exception e) {
				
			}
		}*/
		
		
		   /* driver.findElement(By.xpath("//input[@id='card_number' and contains(@data-form_id,'credit_card_form')]")).sendKeys(CardNumber);
		    driver.findElement(By.xpath("//input[@id='name_on_card' and contains(@data-form_id,'credit_card_form')]")).sendKeys(name);
		    driver.findElement(By.xpath("//input[@id='card_exp_month' and contains(@data-form_id,'credit_card_form')]")).sendKeys(exp);
		    driver.findElement(By.xpath("//input[@id='card_exp_year' and contains(@data-form_id,'credit_card_form')]")).sendKeys(year);
		    driver.findElement(By.xpath("//input[@id='security_code' and contains(@data-form_id,'credit_card_form')]")).sendKeys(cvv);*/
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		System.out.println("Credit Card Number is not Entered.");
	}

}
}
