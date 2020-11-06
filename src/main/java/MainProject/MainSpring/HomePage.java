package MainProject.MainSpring;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseSetup.BaseSetup;

public class HomePage extends BaseSetup {
	
	public HomePage(WebDriver driver) {
		BaseSetup.driver= driver ;
	}
	
	@FindBy(xpath="//div[@class='left_menu_icon_overlay']")
	public WebElement burgerIcon;
	
	@FindBy(xpath="//a[text()='Common Agile Exe Proj_9th Oct 2020']")
	public WebElement project;
		
	public void search() {
		
		burgerIcon.click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ProjectPage pSearch() {
		
		project.click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return PageFactory.initElements(driver, ProjectPage.class);
	}

}
