package MainProject.MainSpring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseSetup.BaseSetup;

public class ProjectPage extends BaseSetup{
	
	public ProjectPage(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
	
	public TestEventPage tabSelect() {
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  Actions action = new Actions(driver);
		  
		  WebElement execute = driver.findElement(By.xpath("//a[@id='LOCK_Monitor']"));
		  action.moveToElement(execute).perform();
		  
		  try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  driver.findElement(By.xpath("//a[@id='LOCK_Action_Items']")).click();
		  
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return PageFactory.initElements(driver, TestEventPage.class);
	}

}
