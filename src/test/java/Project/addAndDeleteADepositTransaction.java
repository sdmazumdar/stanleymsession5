package Project;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class addAndDeleteADepositTransaction {	
	/*Given user goes to TechFios site (https://techfios.com/test/billing/?ng=admin/)
		When user logs in with valid credentials (techfiosdemo@gmail.com/abc123)
		Then Dashboard page should display
		When user clicks on Add Deposit button from Dashboard page
		Then Add Deposit panel should display
		When user selects an account (Advance Option: Randomly select an option)
		And selects todays date (Advance option: Select a future date certain days from now)
		And enters a unique description
		And enter any amount
		And clicks on the Advanced link
		And selects a random category
		And enters any tag
		And randomly selects a player
		And selects Method : Credit Card
		And enters a unique Ref#
		And Clicks Submit
		Then " Transaction Added Successfully " message should display
		And unique description should be found in the list
		When user clicks on the newly created transaction
		Then Edit Transaction panel should display
		When clicks on the Delete button
		Then " Transaction Deleted Successfully " message should display
		And the transaction description should no longer be found in the list*/
	
	@Test
	public void AddDeleteADeposit() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@name='login']")).click();
		Thread.sleep(2000);
		
		System.out.println(driver.getTitle());
		String ActualTitle1=driver.getTitle();
		String ExpectedTitle1="Dashboard- TechFios Test Application - Billing";
		Assert.assertTrue("Dashboard did not displayed.",ExpectedTitle1.equalsIgnoreCase(ActualTitle1) );
		
		driver.findElement(By.xpath("//span[contains(text(),'Transactions')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'New Deposit')]")).click();
		System.out.println(driver.findElement(By.xpath("//h5")).getText());
		
		String ActualTitle2=driver.findElement(By.xpath("//h5")).getText();
		String ExpectedTitle2="Add Deposit";
		Assert.assertTrue("Add Deposit did not displayed.",ExpectedTitle2.equalsIgnoreCase(ActualTitle2));
		
		WebElement AccountType = driver.findElement(By.xpath("//select[@id='account']"));
		Select select1 = new Select(AccountType);
		select1.selectByIndex(3);
		
		Random random = new Random();
		String description = "Stanley_"+random.nextInt(999);
		driver.findElement(By.xpath("//input[@id='description']")).sendKeys(description);
		driver.findElement(By.xpath("//input[@id='amount']")).sendKeys("100");
		driver.findElement(By.xpath("//a[contains(text(),'Advanced')]")).click();
		
		WebElement CategoryType = driver.findElement(By.xpath("//select[@id='cats']"));
		Select select2 = new Select(CategoryType);
		select2.selectByIndex(6);
		
		//need help to find tag
		//driver.findElement(By.xpath("//select[@id='tags']")).sendKeys("input tag");
		
		WebElement payer = driver.findElement(By.xpath("//select[@id='payer']"));
		Select select3=new Select (payer);
		select3.selectByIndex(4);
		
		WebElement PaymentMethod = driver.findElement(By.xpath("//select[@id='pmethod']"));
		Select select4 = new Select(PaymentMethod);
		select4.selectByIndex(3);
		
		driver.findElement(By.xpath("//input[@id='ref']")).sendKeys("123");
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(4000);
		
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText());
		String ActualTitle3=driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText();
		String ExpectedTitle3="Transaction Added Successfully";
		Assert.assertTrue("Transaction not added",ActualTitle3.contains(ExpectedTitle3));
		
		driver.findElement(By.xpath("//a[contains(text(),'View Transactions')]")).click();
		
		//need help to dynamic this xpath by description to find the unique description
		driver.findElement(By.xpath("//td[text()='"+description+"']/parent::*//a[text()='Manage']")).click();
		Thread.sleep(4000);
		
		System.out.println(driver.findElement(By.xpath("//h5")).getText());
		String ActualTitle4 =driver.findElement(By.xpath("//h5")).getText();
		String ExpectedTitle4="Edit Transaction";
		Assert.assertTrue("Not possible to edit",ActualTitle4.contains(ExpectedTitle4) );
		
		driver.findElement(By.xpath("//button[contains(text(),' Delete')]")).click();
		//Validation of success msg shows up
		Assert.assertTrue("Msg did not display! " + "The description was " + description , driver.getPageSource().contains("Transaction Deleted Successfully"));
		//Validate the unique description deleted from the table
		Assert.assertFalse("Deposit was not deleted!", driver.getPageSource().contains(description));
		Assert.assertTrue("Deposit was not deleted!", !driver.getPageSource().contains(description));
		
		
		
		Thread.sleep(2000);
		
		
		driver.quit();
		
	} 
	
	
	

}
