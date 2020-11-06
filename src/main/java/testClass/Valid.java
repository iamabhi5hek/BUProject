package testClass;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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

public class Valid extends BaseSetup{
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
		logger = report.createTest("Valid test cases");
		
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
	

	@Test(dataProvider="validcase")
	public void VerifyinvalidData(String name, String description,String action_item,String priority,String module,String expected_date,String expected_result) throws IOException {

		clearField(prop.getProperty("Name"));
		addValues(prop.getProperty("Name"), name);
		clearField(prop.getProperty("Description"));
		addValues(prop.getProperty("Description"), description);

		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("source_of_action_item"))));
		action.selectByVisibleText(action_item);

		Select pri = new Select(driver.findElement(By.xpath(prop.getProperty("priority"))));
		pri.selectByVisibleText(priority);
		timewait(5);
		clearField(prop.getProperty("expected_date"));
		addValues(prop.getProperty("expected_date"), expected_date);

		driver.findElement(By.xpath(prop.getProperty("save"))).click();
		timewait(20);
		String mess = "simple ACT number";
		mess=driver.findElement(By.xpath(prop.getProperty("actid"))).getText();
		
		System.err.println(mess+" is saved.");
		
		Assert.assertTrue(mess.contains(expected_result));
		
		timewait(20);
		
		driver.findElement(By.xpath(prop.getProperty("addnew"))).click();

	}

	@DataProvider(name="validcase")
	public Object[][] validcaseData() {
		Object[][] arrayObject = getExcelData(System.getProperty("user.dir") +"\\MainSpring Testing.xlsx","valid",8,8);
		return arrayObject;
	}
}
