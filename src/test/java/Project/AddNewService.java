package Project;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddNewService {
	@Test
	public void addNewService() throws InterruptedException {
		/*Given user goes to TechFios site (https://techfios.com/test/billing/?ng=admin/)
			When user logs in with valid credentials (techfiosdemo@gmail.com/abc123)
			Then Dashboard page should display
			When user goes to Products & Services > New Service page
			Then Add Service panel should display
			When user adds any new service with a random item number/description
			Then Item Added Successfully message should display
			When user clicks on List Service
			And searches for the new service
			Then new Service should display
		 * */
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=login/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@name='login']")).click();
		Thread.sleep(1000);
		
		System.out.println(driver.getTitle());
		String ExpectedTitle = "Dashboard- TechFios Test Application - Billing";
		String ActualTitle = driver.getTitle();
		Assert.assertTrue("Dashboard did not displayed",ExpectedTitle.equalsIgnoreCase(ActualTitle) );
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//span[contains(text(),'Products & Services')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'New Service')]")).click();
		
		System.out.println(driver.findElement(By.xpath("//h5[contains(text(),'Add')]")).getText());
		String ExpectedTitle1="Add Service";
		String ActualTitle1=driver.findElement(By.xpath("//h5[contains(text(),'Add')]")).getText();
		Assert.assertTrue("Add service page did not displayed",ExpectedTitle1.equalsIgnoreCase(ActualTitle1));
		
		Random random= new Random();
		String number = "new service_"+ random.nextInt(999);
		driver.findElement(By.id("name")).sendKeys(number);
		driver.findElement(By.id("sales_price")).sendKeys("100");
		driver.findElement(By.id("item_number")).sendKeys("2000");
		driver.findElement(By.id("description")).sendKeys("description_"+number);
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(3000);
		
		//need help to verify the faded text by assert
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText());
		String ExpectedTitle2="Item Added Successfully";
		String ActualTitle2 = driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText();
		Assert.assertTrue("Item not added successfully", ActualTitle2.contains(ExpectedTitle2));
		
		driver.findElement(By.xpath("//a[contains(text(),'List Services')]")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@id='txtsearch']")).sendKeys(number);
		driver.findElement(By.xpath("//button[@id='search']")).click();
		Thread.sleep(4000);
		System.out.println(driver.findElement(By.xpath("//a[contains(text(),'" +number+ "')]")));
		
		driver.findElement(By.xpath("//a[@class='btn btn-danger btn-sm cdelete']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
		Thread.sleep(3000);
	
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")));
		String ExpectedTitle3="Service Deleted Successfully";
		String ActualTitle3 = driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText();
		Assert.assertTrue("Item not deleted successfully", ActualTitle3.contains(ExpectedTitle3));
		
		driver.quit();
		
		
		
	}

}
