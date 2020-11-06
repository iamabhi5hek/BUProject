package testClass;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import MainProject.MainSpring.HomePage;
import MainProject.MainSpring.LoginMail;
import MainProject.MainSpring.LoginOTP;
import MainProject.MainSpring.LoginPassword;
import MainProject.MainSpring.LoginRem;
import MainProject.MainSpring.ProjectPage;
import MainProject.MainSpring.TestEventPage;
import baseSetup.BaseSetup;

public class Invalid extends BaseSetup{
	LoginMail le;
	LoginPassword lp;
	LoginOTP lo;
	LoginRem lr;
	HomePage home;
	ProjectPage pro;
	TestEventPage te;
	
	static Properties prop = readProperties();
	
	@BeforeTest
	public void Test() throws Exception {
		logger = report.createTest("Invalid test cases");
		
		logger.log(Status.INFO, "Opening the browser");
		invokebrowser();
		logger.log(Status.PASS, "Browser successfully launched");
		
		logger.log(Status.INFO, "Opening the url");
		le = openUrl();
		logger.log(Status.PASS, "Main spring site successfully launched");
		
		logger.log(Status.INFO, "Entering email, password,otp");
		lp = le.enterEmail();
		
		lo=lp.enterPassword();
		
		lr = lo.OTPVerification();
		
		home=lr.RememberMe();
		logger.log(Status.PASS, "Successfully entered details");
		
		logger.log(Status.INFO, "Opening module");
		home.search();
		timewait(20);
		pro =home.pSearch();
		timewait(20);
		te=pro.tabSelect();
		timewait(20);
		te.addTest();
		logger.log(Status.PASS, "Module successfully");
		
		driver.switchTo().frame("contentframe");
				
	}
	

	@Test(dataProvider="invalidcase")
	public void VerifyinvalidData(String name, String description,String action_item,String priority,String module,String expected_date,String expected_result) throws IOException {

		clearField(prop.getProperty("Name"));
		addValues(prop.getProperty("Name"), name);
		clearField(prop.getProperty("Description"));
		addValues(prop.getProperty("Description"), description);

		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("source_of_action_item"))));
		action.selectByVisibleText(action_item);

		Select pri = new Select(driver.findElement(By.xpath(prop.getProperty("priority"))));
		pri.selectByVisibleText(priority);

		clearField(prop.getProperty("expected_date"));
		addValues(prop.getProperty("expected_date"), expected_date);

		driver.findElement(By.xpath(prop.getProperty("save"))).click();

		String mess = "simple Saved the result";
		if(isAlertPresent()==true)
		{mess=driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();

		}
		System.err.println(mess);
		assertTrue(mess.contains(expected_result));

	}

	@DataProvider(name="invalidcase")
	public Object[][] invalidcaseData() {
		Object[][] arrayObject = getExcelData(System.getProperty("user.dir") +"\\MainSpring Testing.xlsx","invalid",8,8);
		return arrayObject;
	}
}
