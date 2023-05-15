package Project;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class addanddeleteaquote {
	@Test
	public void AddDeleteQuote() throws InterruptedException {
		/*Given user goes to TechFios site (https://techfios.com/test/billing/?ng=admin/)
			When user logs in with valid credentials (techfiosdemo@gmail.com/abc123)
			Then Dashboard page should display
			When user goes to Sales > Create New Quote page
			Then Create New Quote panel should display
			When user creates a new quote with unique Subject
			And goes to Quotes page
			Then new quote should exist
			When user delete the quote
			Then quote should be deleted*/
	
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
		driver.findElement(By.linkText("Create New Quote")).click();
		Thread.sleep(2000);
		Assert.assertTrue("Create new quote didn't displayed.", driver.getPageSource().contains("Create New Quote / Proposal / Estimate"));
		Random random = new Random();
		String uniqueSubject = "uniqSub_"+random.nextInt(999);
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(uniqueSubject);
		WebElement customerLocator = driver.findElement(By.xpath("//select[@id='cid']"));
		Select select1=new Select(customerLocator);
		select1.selectByIndex(8);
		driver.findElement(By.id("invoicenum")).sendKeys("check");
		WebElement stageLocator = driver.findElement(By.xpath("//select[@id='stage']"));
		Select select2=new Select(stageLocator);
		select2.selectByIndex(3);
		WebElement taxLocator = driver.findElement(By.xpath("//select[@id='tid']"));
		Select select3=new Select(taxLocator);
		select3.selectByIndex(1);
		driver.findElement(By.xpath("//div[@dir='ltr']")).sendKeys("test proposal");
		driver.findElement(By.xpath("//button[contains(text(),' Add blank Line')]")).click();
		driver.findElement(By.xpath("//textarea[@name='desc[]']")).sendKeys("TV");
		driver.findElement(By.xpath("//input[@name='qty[]']")).sendKeys("2");
		driver.findElement(By.xpath("//input[@name='amount[]']")).sendKeys("200");
		WebElement staxLocator = driver.findElement(By.xpath("//select[@name='taxed[]']"));
		Select select4=new Select(staxLocator);
		select4.selectByIndex(0);
		driver.findElement(By.xpath("//button[@id='submit']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[@id='save_n_close']")).click();
		Thread.sleep(4000);
		driver.findElement(By.linkText("Quotes")).click();
		
		List<WebElement> quote = driver.findElements(By.xpath("//table//td[3]"));
		int counter = 0;
		for(WebElement w: quote) {
			if(w.getText().contains(uniqueSubject)) {
				System.out.println("Quote found..!!");
			}else if(counter==quote.size()) {
				System.out.println("Quote not found..!!");
				throw new RuntimeException("Test failed.");
			}
			counter ++;
		}
		
		//need to find the delete dynamic xpath
		//driver.findElement(By.xpath("//table//a[contains(text(),'uniqSub_817')]//td[@class='text-right']//a[contains(text(),'Delete')]")).click();
		//Thread.sleep(4000);
		
		
		driver.quit();
	
	}

}
