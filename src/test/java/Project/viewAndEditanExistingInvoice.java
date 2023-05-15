package Project;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class viewAndEditanExistingInvoice {

	    /*Given user goes to TechFios site (https://techfios.com/test/billing/?ng=admin/)
        When user logs in with valid credentials (techfiosdemo@gmail.com/abc123)
		Then Dashboard page should display
		When user goes to Sales > Invoices page
		Then Invoices page should display
		When user click on the view button of an existing invoice
		Then the invoice should display
		When user clicks on edit button
		And update information specially with new invoice number
		And clicks on save invoice
		Then invoice should display the newly saved data*/
	
	@Test
	public void viewEditInvoice() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.xpath("//button")).click();
		System.out.println(driver.getTitle());
		String expectedTitle = "Dashboard- TechFios Test Application - Billing";
		String actualTitle = driver.getTitle();
		Assert.assertTrue("Dashboard didn't displayed.",expectedTitle.contains(actualTitle));
		driver.findElement(By.xpath("//span[contains(text(),'Sales')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Invoices")).click();
		driver.findElement(By.xpath("//a[contains(text(),' View')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),' Edit')]")).click();
		
		//Actions act = new Actions(driver);
		//act.sendKeys(Keys.BACK_SPACE).perform();
		driver.findElement(By.id("cn")).clear();
		driver.findElement(By.id("cn")).sendKeys("1000");
		WebElement salesTax = driver.findElement(By.xpath("//select[@id='tid']"));
		Select select = new Select(salesTax);
		select.selectByIndex(1);
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor)driver;
		js1.executeScript("window.scroll(0,800)");
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(5000);
		System.out.println(driver.findElement(By.xpath("//h5")).getText());
	
		driver.quit();
		
	}
	
}
