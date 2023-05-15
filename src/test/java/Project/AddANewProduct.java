package Project;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddANewProduct {

	/*
	 * Given user goes to TechFios site
	 * (https://techfios.com/test/billing/?ng=admin/) When user logs in with valid
	 * credentials (techfiosdemo@gmail.com/abc123) Then Dashboard page should
	 * display When user goes to Products & Services > New Products page Then Add
	 * Product panel should display When user adds any new product with a random
	 * item number Then Item Added Successfully message should display When user
	 * clicks on List Products And searches for the new product Then new product
	 * should display
	 */

	WebDriver driver;
	
	@Test
	public void addANewProduct() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=login/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@name='login']")).click();
		Thread.sleep(2000);
		
		System.out.println(driver.getTitle());
		String ExpectedTitle1 = "Dashboard- TechFios Test Application - Billing";
		String ActualTitle1 = driver.getTitle();
		Assert.assertTrue("Dashboard did not displayed",ActualTitle1.equalsIgnoreCase(ExpectedTitle1) );
		
		driver.findElement(By.xpath("//span[contains(text(),'Products & Services')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("New Product")).click();
		
		System.out.println(driver.findElement(By.xpath("//div[@class='ibox-title']")).getText());
		String ExpectedTitle2 = "Add Product";
		String ActualTitle2 = driver.findElement(By.xpath("//div[@class='ibox-title']")).getText();
		//Assert.assertTrue("Add Product did not displayed",ActualTitle2.equalsIgnoreCase(ExpectedTitle2) );
		
		//Unique product name
		Random rnd = new Random();
		String expectedProduct = "LG-TV-" + rnd.nextInt(9999);
		
		
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys(expectedProduct);
		driver.findElement(By.xpath("//input[@id='sales_price']")).sendKeys("200");
		driver.findElement(By.xpath("//input[@id='item_number']")).sendKeys("4567");
		driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys("on sale");
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(3000);
		
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText());
		String expectedMsg = "Item Added Successfully";
		String ActualMsg = driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText();
		Assert.assertTrue("Item not added",ActualMsg.toLowerCase().contains(expectedMsg.toLowerCase()) );
		Thread.sleep(3000);
		
		driver.findElement(By.linkText("List Products")).click();
			
		Thread.sleep(3000);
			
		//Validation 1
//		driver.findElement(By.xpath("//input[@id='txtsearch']")).sendKeys(expectedProduct);
//		driver.findElement(By.xpath("//button[@id='search']")).click();
//		Thread.sleep(3000);
	
//		String actualProduct = driver.findElement(By.xpath("//table//tbody//tr[1]//td[1]")).getText();
//		Assert.assertTrue("Product not found!\n" 
//				+ "Expected Product: " + expectedProduct
//				+ "Actual Product: " + actualProduct
//				, actualProduct.toLowerCase().contains(expectedProduct.toLowerCase()));
		
		//Validation using List comparison
		List<WebElement> productElements = driver.findElements(By.xpath("//table//tbody//tr//td[1]"));
		
		int counter = 1;
		for(WebElement w: productElements) {
			if(w.getText().contains(expectedProduct)) {
					System.out.println("Product found!");
					break;
			} else if (counter == productElements.size()) {
				System.out.println("Product not found!");
				throw new RuntimeException("Test failed!");
			}
			counter++;
		}
	
	}
	
	@After
	public void close() {
		driver.quit();
	}

}
