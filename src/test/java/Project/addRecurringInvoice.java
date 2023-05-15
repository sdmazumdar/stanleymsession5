package Project;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;



public class addRecurringInvoice {
	@Test
	public void recurringInvoice () throws InterruptedException {
		/*Given user goes to TechFios site (https://techfios.com/test/billing/?ng=admin/)
		When user logs in with valid credentials (techfiosdemo@gmail.com/abc123)
		Then Dashboard page should display
		When user goes to Sales > New Recurring Invoice Page
		Then Recurring Invoices page should display
		When user clicks on Add Recurring Invoice
		Then Create Recurring Invoice panel should display
		When user creates a new recurring invoice 
		Then new invoice should display
		When user goes back to New Recurring Invoice page
		Then created recurring invoice should display*/
		
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.name("login")).click();
		Thread.sleep(2000);
		
		String actualTitle= driver.getTitle();
		String expectedTitle = "Dashboard- TechFios Test Application - Billing";
		Assert.assertTrue("Dashboard didn't displayed", expectedTitle.equalsIgnoreCase(actualTitle));
		
		driver.findElement(By.xpath("//span[contains(text(),'Sales')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("New Recurring Invoice")).click();
		
		String actualTitle1= driver.findElement(By.xpath("//h5")).getText();
		String expectedTitle1 = "Create Recurring Invoice";
		Assert.assertTrue("New Recurring Invoice didn't displayed", expectedTitle1.contains(actualTitle1));
		
		/*
		 * driver.findElement(By.linkText("Recurring Invoices")).click();
		 * driver.findElement(By.xpath("//a[contains(text(),' Add Recurring Invoice')]")
		 * ).click();
		 * 
		 * String actualTitle2= driver.findElement(By.xpath("//h5")).getText(); String
		 * expectedTitle2 = "Create Recurring Invoice";
		 * Assert.assertTrue("New Recurring Invoice didn't displayed",
		 * expectedTitle2.contains(actualTitle2)); Thread.sleep(3000);
		 */
		
		WebElement customerLocator = driver.findElement(By.xpath("//select[@id='cid']"));
		Select select1=new Select(customerLocator);
		select1.selectByIndex(5);
		
		Random random = new Random();
		String invoiceNumber= "test"+random.nextInt(999);
		
		driver.findElement(By.id("invoicenum")).sendKeys("check_");
		driver.findElement(By.id("cn")).sendKeys(invoiceNumber);
		
		WebElement repeatLocator = driver.findElement(By.xpath("//select[@id='repeat']"));
		Select select2=new Select(repeatLocator);
		select2.selectByIndex(5);
		
		WebElement paymentTermLocator = driver.findElement(By.xpath("//select[@id='duedate']"));
		Select select3=new Select(paymentTermLocator);
		select3.selectByIndex(4);
		
		WebElement taxLocator = driver.findElement(By.xpath("//select[@id='tid']"));
		Select select4=new Select(taxLocator);
		select4.selectByIndex(1);
		
		driver.findElement(By.xpath("//button[contains(text(),' Add blank Line')]")).click();
		driver.findElement(By.xpath("//input[@name='desc[]']")).sendKeys("phone2");
		driver.findElement(By.xpath("//input[@name='qty[]']")).sendKeys("2");
		driver.findElement(By.xpath("//input[@name='amount[]']")).sendKeys("200");
		
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scroll(0,600)");
		
		driver.findElement(By.xpath("//div[@dir='ltr']")).sendKeys("description");
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(5000);
		Assert.assertTrue("invoice not added..!!", driver.getPageSource().contains(invoiceNumber));
		
		driver.findElement(By.linkText("Invoices")).click();
		
		List<WebElement> invoiceList = driver.findElements(By.xpath("//table//td/a[1]"));
		int counter = 1;
		for(WebElement w:invoiceList ) {
			if(w.getText().contains(invoiceNumber)) {
				System.out.println("Invoice found..!!");
				break;
			}else if(counter==invoiceList.size()) {
				System.out.println("Invoice not found..!!");
				throw new RuntimeException("Test failed!");
			}
			counter++;
		}
		
		driver.quit();
		
		
		
	}

}
