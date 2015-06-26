package com.testproject.java;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.testproject.java.framework.pageobject.DetailPage;
import com.testproject.java.framework.pageobject.HomePage;
import com.testproject.java.framework.pageobject.ResultItem;
import com.testproject.java.framework.pageobject.ResultsPage;

public class DetailsPageScripts {

	static WebDriver driver;

	String keyword = "Galaxy";
//	String keyword = "Galaxy S6"; 
//	String keyword = "iPhone";
//	String keyword = "iPhone 6";

	@BeforeClass
	public static void setUp() 
	{
		System.setProperty("webdriver.chrome.driver",
				"C:\\Selenium\\BrowserDrivers\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() 
	{
		driver.quit();
	}
	
//	Input file: bestbuy2.txt - iPhone 6, Galaxy and iPhone
	@Test
	public void testProductDetail() throws InterruptedException 
	{
		ResultsPage resultsPage = new HomePage(driver).search(this.keyword);

		ResultItem resultItem = resultsPage.getResult(1);	
	
		assertTrue(resultItem.isPriceDisplayed());

		Double priceResultItem = resultItem.getPrice();    
		String titleResultItem = resultItem.getTitle();   
		
		DetailPage detailPage = resultItem.clickResult();

		assertTrue(detailPage.isImageDisplayed());

		assertTrue(detailPage.isImageUrlValid());

		assertEquals(priceResultItem, detailPage.getPrice());

		assertTrue(detailPage.isOnlinePurchaseInfoDisplayed());

		assertTrue(detailPage.isInStorPurchaseInfoDisplayed());

		assertTrue(detailPage.isWishListDisplayed());

		assertEquals(titleResultItem, detailPage.getTitle());
	}
}



















