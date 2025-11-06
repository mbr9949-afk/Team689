package Tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Pages.HomePage;
import Pages.ProductsPage;
import Pages.SignupLoginPage;
import Utilities.BaseClass;

public class testcase extends BaseClass
{
	@Test
	public void registerUser()
	{

			test=extent.createTest("Register User - Valid Data");
				
			HomePage homepageobject					=PageFactory.initElements(driver,HomePage.class);
			SignupLoginPage signuploginpageobject	=PageFactory.initElements(driver,SignupLoginPage.class);
			
			click(homepageobject.Logo);
			
			
			// assertion for home page is displayed	 or not
			Assert.assertEquals("Automation Exercise", driver.getTitle());
			test.log(Status.PASS, "Home Page is Displayed");
						
			
			click(homepageobject.Signup_Login);
			test.log(Status.INFO, "Navigate to Signup Page");
			
			Assert.assertEquals("New User Signup!", signuploginpageobject.SignupText.getText());
			test.log(Status.PASS, "New User Signup! Text is Displayed");
			
	
			
			enterData(signuploginpageobject.SignupName,config.getProperty("Name"));		
			enterData(signuploginpageobject.SignupEmail,config.getProperty("Email"));
			test.log(Status.INFO, "Entered  Signup details are : "+config.getProperty("Name")+": , "+config.getProperty("Email"));
			
			click(signuploginpageobject.SignupButton);
			test.log(Status.INFO, "Navigate to Signup Page");
			
			Assert.assertEquals(true, signuploginpageobject.accinfotext.isDisplayed());
			test.log(Status.PASS, "ENTER ACCOUNT INFORMATION Text is Displayed");
			
	}
	@Test
	public void login()

	{
		test=extent.createTest("Register User - Valid Data");
		
		HomePage homepageobject					=PageFactory.initElements(driver,HomePage.class);
		SignupLoginPage signuploginpageobject	=PageFactory.initElements(driver,SignupLoginPage.class);
		
		click(homepageobject.Logo);
		
		// assertion for home page is displayed	 or not
		Assert.assertEquals("Automation Exercise", driver.getTitle());
		test.log(Status.PASS, "Home Page is Displayed");
					
		
		click(homepageobject.Signup_Login);
		test.log(Status.INFO, "Navigate to Signup Page");
		
		Assert.assertEquals("New User Signup!", signuploginpageobject.SignupText.getText());
		test.log(Status.PASS, "New User Signup! Text is Displayed");
		

		
		enterData(signuploginpageobject.SignupName,config.getProperty("Name"));		
		enterData(signuploginpageobject.SignupEmail,config.getProperty("Email"));
		test.log(Status.INFO, "Entered  Signup details are : "+config.getProperty("Name")+": , "+config.getProperty("Email"));
		
		click(signuploginpageobject.SignupButton);
		test.log(Status.INFO, "Navigate to Signup Page");
		
		Assert.assertEquals(true, signuploginpageobject.accinfotext.isDisplayed());
		test.log(Status.PASS, "ENTER ACCOUNT INFORMATION Text is Displayed");

	}
}
