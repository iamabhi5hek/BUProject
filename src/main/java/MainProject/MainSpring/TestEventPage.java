package MainProject.MainSpring;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import baseSetup.BaseSetup;

public class TestEventPage extends BaseSetup{
	
	public TestEventPage(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
	@FindBy(xpath="//span[@id='KEY_BUTTON_Add-btnIconEl']")
	public WebElement createTest;
	
	
	public void addTest() {
		createTest.click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
