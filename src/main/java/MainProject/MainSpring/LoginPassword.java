package MainProject.MainSpring;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseSetup.BaseSetup;

public class LoginPassword extends BaseSetup{
	static Properties prop = readProperties();
	
	public LoginPassword(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
	
	public LoginOTP enterPassword() {
		WebDriverWait wait = new WebDriverWait(driver,100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwd")));						//Wait to locate password element
		driver.findElement(By.name("passwd")).sendKeys(prop.getProperty("Password"));											//Sending password
		driver.findElement(By.id("idSIButton9")).click();	
		
		return PageFactory.initElements(driver, LoginOTP.class);//Password submit
	}
	
	
	

}
