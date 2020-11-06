package MainProject.MainSpring;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseSetup.BaseSetup;

public class LoginMail extends BaseSetup {
	static Properties prop = readProperties();
	
	public LoginMail(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
		
	public LoginPassword enterEmail() {
		
		WebDriverWait wait = new WebDriverWait(driver,100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));		//Wait to locate Email element
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(prop.getProperty("Email"));							//Sending email id
		driver.findElement(By.xpath("//input[@type='submit']")).click();									//Email submit
		
		return PageFactory.initElements(driver, LoginPassword.class);
	}

}
