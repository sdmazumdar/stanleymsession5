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

public class addAndDeleteAnInvoice {
	/*
	 * Given user goes to TechFios site
	 * (https://techfios.com/test/billing/?ng=admin/) When user logs in with valid
	 * credentials (techfiosdemo@gmail.com/abc123) Then Dashboard page should
	 * display When user goes to Sales > New Invoice page Then Create New Invoice
	 * panel should display When user fills in the new invoice (challenge yourself
	 * here to use all elements) And clicks on Save Invoice button Then new invoice
	 * should display with provided invoice number When user goes to Invoices page
	 * Then new invoice should exist in the list When user deletes that invoice Then
	 * Invoice Deleted Successfully message should display And that invoice should
	 * no longer exist in the list
	 */

	@Test
	public void AddDeleteAnInvoice() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=login/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@name='login']")).click();
		Thread.sleep(2000);

		System.out.println(driver.getTitle());
		String ActualTitle1 = driver.getTitle();
		String ExpectedTitle1 = "Dashboard- TechFios Test Application - Billing";
		Assert.assertTrue("Dashboard didn't display", ExpectedTitle1.equalsIgnoreCase(ActualTitle1));

		driver.findElement(By.xpath("//span[contains(text(),'Sales')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'New Invoice')]")).click();

		System.out.println(driver.findElement(By.xpath("//h5")).getText());
		String ActualTitle2 = driver.findElement(By.xpath("//h5")).getText();
		String ExpectedTitle2 = "Create New Invoice";
		Assert.assertTrue("New Invoice page didn't display", ExpectedTitle2.equalsIgnoreCase(ActualTitle2));

		WebElement customerDropDownList = driver.findElement(By.xpath("//select[@id='cid']"));
		Select select1 = new Select(customerDropDownList);
		select1.selectByIndex(3);

		Random random = new Random();
		// String invoiceprefix= "test_";
		String invoicenumber = "" + random.nextInt(999);

		driver.findElement(By.id("invoicenum")).sendKeys("test_");
		driver.findElement(By.id("cn")).sendKeys(invoicenumber);

		WebElement paymentTermsLocator = driver.findElement(By.xpath("//select[@id='duedate']"));
		Select select2 = new Select(paymentTermsLocator);
		select2.selectByIndex(3);

		WebElement salesTaxLocator = driver.findElement(By.xpath("//select[@id='tid']"));
		Select select3 = new Select(salesTaxLocator);
		select3.selectByIndex(1);
		// need to check the discount option
		// driver.findElement(By.xpath("//a[@id='add_discount']")).click();
		// need to check the radio button
		// driver.findElement(By.xpath("//input[@id='set_discount_type-1']")).click();
		// driver.findElement(By.xpath("//label[@for='set_discount_type-1']")).click();
		// driver.findElement(By.xpath("//input[@id='set_discount']")).sendKeys("10");
		// driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
		driver.findElement(By.xpath("//button[@id='blank-add']")).click();
		driver.findElement(By.xpath("//input[@name='desc[]']")).sendKeys("TV");
		driver.findElement(By.xpath("//input[@name='qty[]']")).sendKeys("2");
		driver.findElement(By.xpath("//input[@name='amount[]']")).sendKeys("150");

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scroll(0,600)");

		driver.findElement(By.xpath("//div[@class='redactor-editor redactor-placeholder']"))
				.sendKeys("Warranty 6 months");
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[contains(text(),'Sales')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Invoices')]")).click();

		// how to check the newly added invoice from the list?
		List<WebElement> invoiceElements = driver.findElements(By.xpath("//table/tbody/tr/td/a[1]"));
		int counter = 1;
		for (WebElement element : invoiceElements) {
			if (element.getText().contains(invoicenumber)) {
				System.out.println("Invoice found");
				break;
			} else if (counter == invoiceElements.size()) {
				System.out.println("Invoie not found");
				throw new RuntimeException("Test failed.");
			}
			counter++;
		}

		driver.findElement(By.xpath("//table/tbody/tr/td/a[1][contains(text(),'379')]")).click();

		// Navigate back to Invoices page
		// driver.findElement(By.xpath("//span[contains(text(),'Sales')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Invoices')]")).click();

		// need to find the dynamic xpath of delete button.
		driver.findElement(By.xpath("//table//tbody//tr//td//*[contains(text(),'" + invoicenumber
				+ "')]/ancestor::tr//a[contains(text(),' Delete')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
		Assert.assertTrue("Invoice deleted", driver.getPageSource().contains("Invoice Deleted Successfully"));

		List<WebElement> invoiceList = driver.findElements(By.xpath("//table//tr//td[1]//a"));

		for (WebElement w : invoiceList) {
			if (w.getText().contains(invoicenumber)) {
				throw new RuntimeException("Test Failed..Invoice wasn't Deleted..");
			}
		}

		Thread.sleep(4000);
		driver.quit();

	}

}
