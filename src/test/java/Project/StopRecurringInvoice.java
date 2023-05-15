package Project;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/*Given user goes to TechFios site (https://techfios.com/test/billing/?ng=admin/)
When user logs in with valid credentials (techfiosdemo@gmail.com/abc123)
Then Dashboard page should display
When user goes to Sales > New Recurring Invoice Page
Then Recurring Invoices page should display
When user clicks on Stop Recurring button
And clicks Cancel
Then recurring invoice should continue to exist
When user clicks on Stop Recurring button
And clicks Ok
Then the Recurring Invoice should be removed from the page
 */

public class StopRecurringInvoice {
	@Test
	public void checkRecurringInvoice() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@name='login']")).click();
		
		System.out.println(driver.getTitle());
		String expectedTitle= "Dashboard- TechFios Test Application - Billing";
		String actualTitle = driver.getTitle();
		Assert.assertTrue("Dashboard page did not found.",expectedTitle.equalsIgnoreCase(actualTitle));
		
		driver.findElement(By.xpath("//span[text()='Sales']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='Recurring Invoices']")).click();
		Thread.sleep(3000);
		//Then Recurring Invoices page should display
		
		driver.findElement(By.xpath("//a[text()=' Stop Recurring']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Cancel']")).click();
		driver.findElement(By.xpath("//a[text()=' Stop Recurring']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(2000);
		
		//driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText();
		System.out.println(driver.getTitle());
		
		
		driver.quit();
	}

}
