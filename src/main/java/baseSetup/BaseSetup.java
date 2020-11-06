package baseSetup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import MainProject.MainSpring.LoginMail;
import Utilities.DateUtil;
import Utilities.ExtentReportManager;

public class BaseSetup {

	public ExtentTest logger;
	static Properties prop = readProperties();
	public static WebDriver driver;
	public ExtentReports report = ExtentReportManager.getReportInstance();

	/****************************************
	 * Invoking browser
	 ***************************************************************/

	public void invokebrowser() throws Exception {

		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-infobars");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}

		else {

			FirefoxOptions fo = new FirefoxOptions();
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver(fo);
		}

		// Maximize the browser window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/****************************************
	 * Handling property file
	 ***************************************************************/

	public static Properties readProperties() {
		File file = new File("config.properties");
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/****************************************
	 * URL
	 ***************************************************************/

	public LoginMail openUrl() {

		// Navigate to RelianceDigital Website
		String URL = prop.getProperty("URL");
		driver.navigate().to(URL);

		// Get the title of Website
		String pageTitle = driver.getTitle();

		timewait(10);
		return PageFactory.initElements(driver, LoginMail.class);
	}

	/****************************************
	 * Thread.sleep
	 ***************************************************************/

	public void timewait(int time) {
		try {
			Thread.sleep(time * 500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/****************************************
	 * HTML report generating functions
	 ***************************************************************/

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	public void takeScreenShot() {
		TakesScreenshot ss = (TakesScreenshot) driver;
		File src = ss.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + DateUtil.getTimeStamp() + ".png");

		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + DateUtil.getTimeStamp() + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShot();
		Assert.fail();
	}

	/****************************************
	 * Generic method for clicking element
	 ***************************************************************/

	public void clickElement(String LocatorValue) {

		if (LocatorValue.endsWith("_Id")) {
			driver.findElement(By.id(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_XPath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_ClassName")) {
			driver.findElement(By.className(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_TagName")) {
			driver.findElement(By.tagName(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_LinkText")) {
			driver.findElement(By.linkText(LocatorValue)).click();
		}

	}

	/****************************************
	 * Generic method for explicit wait
	 ***************************************************************/

	public void ExplicitWait(String LocatorValue) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		if (LocatorValue.endsWith("_Id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		} else if (LocatorValue.endsWith("_XPath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		} else if (LocatorValue.endsWith("_ClassName")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(LocatorValue)));
		} else if (LocatorValue.endsWith("_TagName")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(LocatorValue)));
		} else if (LocatorValue.endsWith("_LinkText")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(LocatorValue)));
		}

	}

	/****************************************
	 * Generic method for checking alert
	 ***************************************************************/

	public boolean isAlertPresent() {

		try {
			driver.switchTo().alert();
			System.out.println(" Alert Present");
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	/****************************************
	 * Generic method for reading excel
	 ***************************************************************/

	public String[][] getExcelData(String fileName, String sheetName, int totalNoOfRows, int totalNoOfCols) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sh = wb.getSheet(sheetName);

			arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols - 1];

			for (int i = 1; i < totalNoOfRows; i++) {

				for (int j = 1; j < totalNoOfCols; j++) {
					arrayExcelData[i - 1][j - 1] = String.valueOf(sh.getRow(i).getCell(j));

				}

			}
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}

		return arrayExcelData;
	}

	/****************************************
	 * Generic method for clearing field
	 ***************************************************************/

	public void clearField(String LocatorValue) {
		WebElement element = driver.findElement(By.xpath(LocatorValue));
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);

	}

	/******************************************
	 * Generic method for sending value in web element
	 *****************************************************/

	public void addValues(String LocatorValue, String value) {

		driver.findElement(By.xpath(LocatorValue)).sendKeys(value);
		driver.findElement(By.xpath(LocatorValue)).sendKeys(Keys.ENTER);
	}

	@AfterSuite
	public void flushReports() {
		report.flush();
		driver.close();
	}

}
