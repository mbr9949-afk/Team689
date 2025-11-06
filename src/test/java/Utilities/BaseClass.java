package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass 
{
	public static File f;
	public static FileInputStream fis;
	public static WebDriver driver;
	public static Properties config;
	public static WebDriverWait wait;
	public static Actions action;
	public static String parent;
	
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public void setupReport()
	{
		String reportPath = System.getProperty("user.dir") + "/Reports/Extent_" + getCurrentDateTime() + ".html";
		htmlReporter = new ExtentHtmlReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		 
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("Regression Suite");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	public  String getCurrentDateTime() 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
	
	@AfterSuite
	public void generateReport()
	{
		System.out.println("Generating combined report...");
		extent.flush();
	}
	
	 @AfterMethod
	 public void getResult(ITestResult result)
	    {
	        if(result.getStatus() == ITestResult.FAILURE)
	        {
	            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
	            test.fail(result.getThrowable());
	        }
	        else if(result.getStatus() == ITestResult.SUCCESS)
	        {
	            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
	            
	        }
	        else
	        {
	            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
	            test.skip(result.getThrowable());
	        }
	    }
	 
	 public void screenshot(String filename)
	    {
	    	String screenshotPath= System.getProperty("user.dir")+"/Screenshots/"+filename+".png";
	    	File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        File destFile = new File(screenshotPath);    
	       
	        try 
	        {
	         FileUtils.copyFile(srcFile, destFile);
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	     //   FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(System.getProperty("user.dir")+"/Screenshots/"+filename+".png");
	        
	    }
	 @AfterMethod
	 public void takeScreenshotOnFailure(ITestResult result) 
	 {
	        // ITestResult provides the status of the test just executed
	        if (result.getStatus() == ITestResult.FAILURE) 
	        {
	            // If the test failed, print its name and capture screenshot
	            System.out.println("️Test Failed: " + result.getName());
	            screenshot(result.getName());  // Use the test method name as screenshot filename
	        }
	    }
	
	 @BeforeClass
		public void init() throws Exception
		{
			//Faker faker = new Faker();
			config = new Properties();
			f=new File(System.getProperty("user.dir")+"/src/test/resources/config.properties");
			fis = new FileInputStream(f);
			config.load(fis);
			
			if(config.getProperty("BrowserType").equals("chrome"))
			{	
				driver = new ChromeDriver();
			}else 	if(config.getProperty("BrowserType").equals("firefox"))
			{
				
				driver = new FirefoxDriver();
			}else 	if(config.getProperty("BrowserType").equals("edge"))
			{
				
				driver = new EdgeDriver();
			}else 	if(config.getProperty("BrowserType").equals("safari"))
			{
				
				driver = new SafariDriver();
			}
			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);// implicit wait - for all elements
			wait = new WebDriverWait(driver,Duration.ofSeconds(30));		// explicit wait - for single element using condition
			driver.manage().window().maximize();
			
			driver.get(config.getProperty("URL"));
			action = new Actions(driver);
			parent = driver.getWindowHandle();		
		}
	 @AfterClass
		public void tearDown()
		{
			driver.quit();
		}
	 
	// ------- Generic Methods for Any Automation Project  -- If we use any repeated method in our project we can initialize once here and use multiple times in our project
		public void enterData(WebElement element, String data)
		{
			element.sendKeys(data);
		}
		public void click(WebElement element)
		{
			element.click();
		}
		public void hover(WebElement element)
		{
			action.moveToElement(element).perform();
		}
		public void actionClick(WebElement element)
		{
			action.moveToElement(element).click(element).build().perform();
		}
		public void actionClick(WebElement element1,WebElement element2)
		{
			action.moveToElement(element1).click(element2).build().perform();
		}
		
		public void selectByIndex(WebElement element,int index)
		{
			new Select(element).selectByIndex(index);
		}
		public void deselectByIndex(WebElement element,int index)
		{
			new Select(element).deselectByIndex(index);
		}
		public void selectByValue(WebElement element,String value)
		{
			new Select(element).selectByValue(value);
		}
		public void deselectByValue(WebElement element,String value)
		{
			new Select(element).deselectByValue(value);
		}
		public void selectByVisualText(WebElement element,String value)
		{
			new Select(element).selectByVisibleText(value);
		}
		public void deselectByVisualText(WebElement element,String value)
		{
			new Select(element).deselectByVisibleText(value);
		}
		public void deselectAll(WebElement element)
		{
			new Select(element).deselectAll();
		}
		public WebElement firstSelectedOption(WebElement element)
		{
			WebElement option = new Select(element).getFirstSelectedOption();
			return option;
		}
		public List<WebElement> allSelectedOptions(WebElement element)
		{
			List<WebElement>	options = new Select(element).getAllSelectedOptions();
			return options;
		}
		
		public void switchToAlertAccept()
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			//alert.dismiss();
			//alert.getText();
		}
		public void switchToAlertDismiss()
		{
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			//alert.getText();
		}
		public void switchToFrameByIndex(int index)
		{
			driver.switchTo().frame(index);
		}
		public void switchToFrameByNameOrId(String nameOrId)
		{
			driver.switchTo().frame(nameOrId);
		}
		public void switchToFrameByWebElement(WebElement element)
		{
			driver.switchTo().frame(element);
		}
		public void switchToParentFrame()
		{
			driver.switchTo().parentFrame();
		}
		public void switchToParentWindow(String parent)
		{
			
			driver.switchTo().window(parent);
		}
		public void switchToChildWindow()
		{
			Set<String> childs = driver.getWindowHandles();
			Iterator<String> it = childs.iterator();
			while(it.hasNext())
			{
				String child = it.next();
				if(!parent.equals(child))
				{
					driver.switchTo().window(child);
					break;	// Switch to the first child window and exit loop
				}
			}
		}
	 
	
	
	
	
	

}
