package Project;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

import org.junit.Assert;

public class AddEditAndDeleteAnote {
	/*
	 * Given user goes to TechFios site
	 * (https://techfios.com/test/billing/?ng=admin/) When user logs in with valid
	 * credentials (techfiosdemo@gmail.com/abc123) Then Dashboard page should
	 * display When user goes to Notes page Then Notes page should display When user
	 * creates a new note Then new note should be created When user edits the note
	 * Then note should be edited When user deletes the note Then the note should be
	 * deleted
	 */
	@Test
	public void Notespage() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://techfios.com/test/billing/?ng=login/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@name='login']")).click();
		Thread.sleep(2000);

		System.out.println(driver.getTitle());
		String ExpectedTitle1 = "Dashboard- TechFios Test Application - Billing";
		String ActualTitle1 = driver.getTitle();
		Assert.assertTrue("Dashboard did not displayed", ActualTitle1.equalsIgnoreCase(ExpectedTitle1));

		driver.findElement(By.xpath("//span[contains(text(),'Notes')]")).click();
		System.out.println(driver.getTitle());
		String ExpectedTitle2 = "Notes - TechFios Test Application - Billing";
		String ActualTitle2 = driver.getTitle();
		Assert.assertTrue("Notes page did not displayed", ActualTitle2.equalsIgnoreCase(ExpectedTitle2));

		Random random = new Random();
		String notes = "Notes test_" + random.nextInt(999);
		driver.findElement(By.xpath("//a[contains(text(),' Add New Note ')]")).click();
		driver.findElement(By.xpath("//input[@name='title']")).sendKeys(notes);
		driver.findElement(By.xpath("//textarea[@name='contents']")).sendKeys("textarea " + notes);
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText());

		// driver.findElement(By.xpath("//a[@href=\"http://techfios.com/test/billing/?ng=notes/init/edit/21/\"]/i")).click();
		driver.findElement(By.linkText(notes)).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//textarea[@name='contents']")).sendKeys("Edit textarea " + notes);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(3000);
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText());

		// driver.findElement(By.xpath("//span[contains(text(),'Notes')]")).click();
		// xpath to delete needs to be dynamic. need help
		// driver.findElement(By.xpath("//a[@href=\"http://techfios.com/test/billing/?ng=notes/init/delete/21/\"]")).click();

		// Renavigate back to Notes page
		driver.findElement(By.xpath("//span[contains(text(),'Notes')]")).click();
		
		//to create delete xpath 
		//this
		//By createdNotesDeleteButtonLocator = By.xpath("//a[contains(text(),'" + notes + "')]/ancestor::tr//a[contains(text(),' Delete ')]");
		//driver.findElement(createdNotesDeleteButtonLocator).click();
		
		//or This
		//WebElement createdNotesDeleteButton = driver.findElement(By.xpath("//a[contains(text(),'" + notes + "')]/ancestor::tr//a[contains(text(),' Delete ')]"));
		//createdNotesDeleteButton.click();

		//OR This
		String deletexpath = "//a[contains(text(),'" + notes + "')]/ancestor::tr//a[contains(text(),' Delete ')]";
		driver.findElement(By.xpath(deletexpath)).click();

		Thread.sleep(7000);
		System.out.println(driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText());

		driver.quit();

	}

}
