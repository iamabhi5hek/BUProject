package MainProject.MainSpring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseSetup.BaseSetup;

public class LoginOTP extends BaseSetup{
	
	public LoginOTP(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
	
//	WebDriverWait wait = new WebDriverWait(driver,100);
	
	
	public LoginRem OTPVerification() throws InterruptedException {
		
		Thread.sleep(10000);																				//Time to enter OTP
		driver.findElement(By.xpath("//input[@type='submit']")).click();									//Submitting OTP
		
		return PageFactory.initElements(driver, LoginRem.class);
		
	}

}
