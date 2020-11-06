package MainProject.MainSpring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseSetup.BaseSetup;

public class LoginRem extends BaseSetup{
	
	public LoginRem(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
	
	WebDriverWait wait = new WebDriverWait(driver,100);
	
	public HomePage RememberMe()	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idSIButton9")));					//Waiting for submit button element
		driver.findElement(By.id("idSIButton9")).click();													//Submitting
		
		return PageFactory.initElements(driver, HomePage.class);

	}

}
