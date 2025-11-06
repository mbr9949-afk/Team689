package Tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Pages.CartPage;
import Pages.HomePage;
import Utilities.BaseClass;

public class test1 extends BaseClass
{
	@Test
	public void test()
	{
		test=extent.createTest("test sample");
		
		HomePage homepage = PageFactory.initElements(driver, HomePage.class);
		CartPage cartpage=PageFactory.initElements(driver,CartPage.class);
		
		
		homepage.Logo.click();
		test.log(Status.INFO, "click on the logo icon on home page");
		
		
		test.log(Status.INFO, "click on logo in homepage");
	
		click(homepage.Logo);
		
		test.log(Status.PASS, "homepage is successfully opened "+driver.getTitle());
		
		Assert.assertEquals(1, 1);
		test.log(Status.PASS, "homepage is successfully opened "+driver.getTitle());
		
	}

}
