package com.testproject.java;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
import com.testproject.java.framework.pageobject.DetailItem;
import com.testproject.java.framework.pageobject.HomePage;
import com.testproject.java.framework.pageobject.ResultItem;
import com.testproject.java.framework.pageobject.ResultsPage;

@RunWith(value = Parameterized.class)
public class ResultsPageScripts {

	static WebDriver driver;

	String keyword = "Galaxy";
//	String keyword = "Galaxy S6";
//	String keyword = "iPhone";
//	String keyword = "iPhone 6";

	private String inputData;
	String suggestionValue;

//CSV input	
	private static String filePath = "D:\\DT\\bestbuy2.txt"; 		/* SORT- testProductSummary() 		*/
//	private static String filePath = "D:\\DT\\bestbuy33.txt"; 		/* SORT-testProducSort() 			*/ 
//	private static String filePath = "D:\\DT\\bestbuy34.txt"; 		/* SORT-testProducSort() 			*/
//	private static String filePath = "D:\\DT\\bestbuynull.txt";    /* NAVIGATE Page-testPageNavigate() */ 
		
	public ResultsPageScripts(String inputData) 
	{
		this.inputData = inputData;
	}

	@Parameters
	public static Collection<Object[]> data() throws IOException
	{
		CSVReader csvReader;
		List<String[]> allLines;
		
		csvReader = new CSVReader(new FileReader(filePath));
		allLines = csvReader.readAll();
		csvReader.close();
		
		String [][] stringArray = new String[allLines.size()][];		

		for (int i = 0; i < allLines.size(); i++)
		{
			stringArray[i] = new String[1];
			stringArray[i][0] = allLines.get(i)[0];
		}
		
		Object[][] data = stringArray;
		
		return Arrays.asList(data);
	}
	
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

	@Ignore
	public void testProductSummary() throws InterruptedException 
	{
		HomePage homePage = new HomePage(driver, keyword);

		homePage.open();

		homePage.typeKeyword();

		ResultsPage resultsPage = homePage.clickSearchButton();

		assertTrue(resultsPage.doesResultLabelIncludeKeyword(keyword));

		assertTrue(resultsPage.isResultCountLabelDisplayed());

		ResultItem resultItem;

		int countResultsPage = resultsPage.getCount();
		
		for (int i = 1; i <= countResultsPage; i++) {
			resultItem = resultsPage.getResult(i);

			assertTrue(resultItem.isImageDisplayed());
			assertTrue(resultItem.isImageUrlValid());
			assertTrue(resultItem.isPriceDisplayed());
			assertTrue(resultItem.isPriceValid());

			assertTrue(resultItem.doesNameIncludeKeyword(keyword));
		}
	}
	
//	Input file: bestbuy2.txt - iPhone 6, Galaxy and iPhone
	@Test
	public void testProductDetail() throws InterruptedException 
	{
		keyword = inputData; 
		
		HomePage homePage = new HomePage(driver, keyword);

		homePage.open();

		homePage.typeKeyword();

		ResultsPage resultsPage = homePage.clickSearchButton();

		ResultItem resultItem;
		DetailItem detailItem;
		String resultPrice;
		Boolean resultItemOnSale;
		String resultItemTitle;
		
		int countResultsPage = resultsPage.getCount();
		
		for (int i = 1; i <= countResultsPage; i++) {
			resultItem = resultsPage.getResult(i);
			assertTrue(resultItem.isPriceDisplayed());
			assertTrue(resultItem.isPriceValid());
			resultPrice = resultItem.getPrice();
			resultItemOnSale = resultItem.isResultItemOnSale();
			resultItemTitle = resultItem.getResultItemTitle();
		
			detailItem = resultItem.clickResult();
			assertTrue(detailItem.isImageDisplayed());
			assertTrue(detailItem.isImageUrlValid());
			assertTrue(detailItem.isPriceSameAsResultPrice(resultPrice, resultItemOnSale));
			assertTrue(detailItem.isOnlinePurchaseInfoDisplayed());
			assertTrue(detailItem.isInStorPurchaseInfoDisplayed());
			assertTrue(detailItem.isWishListDisplayed());
			assertTrue(detailItem.isTitleSameAsResultTitle(resultItemTitle));
			
			resultItem.displayResultsPage();
		}
	}
	

//	SORT Price: High to Low
//	Input file: bestbuy34.txt
	@Ignore
	public void testProductSort() throws InterruptedException 
	{
		String[] inputSortKey = inputData.split("\\s*-\\s*");

		keyword = inputSortKey[0];
		String inputSortKeyNumber = inputSortKey[1];

		HomePage homePage = new HomePage(driver, keyword);

		homePage.open();

		homePage.typeKeyword();

		ResultsPage resultsPage = homePage.clickSearchButton();

		assertTrue(resultsPage.doesResultLabelIncludeKeyword(keyword));

		assertTrue(resultsPage.isResultCountLabelDisplayed());

		resultsPage.clickSortSuggestion(inputSortKeyNumber);
		
		ResultItem resultItem;
		String savePrice = "";
		
		int countResultsPage = resultsPage.getCount();
		
		for (int i = 1; i <= countResultsPage; i++) {
			resultItem = resultsPage.getResult(i);

			assertTrue(resultItem.isPriceDisplayed());
			assertTrue(resultItem.isPriceValid());
			if (i > 1)
			{
				assertTrue((savePrice.compareTo(resultItem.getPrice()) >= 0));
			}
			savePrice = resultItem.getPrice();
		}
}

//  Page Navigation	
//  Input file: bestbuynull.txt
//  use keyword 	
	@Ignore 
	public void testPageNavigate() throws InterruptedException
	{
		HomePage homePage = new HomePage(driver, keyword);

		homePage.open();

		homePage.setLanguageToEnglish();
		
		homePage.typeKeyword();

		ResultsPage resultsPage = homePage.clickSearchButton();

		assertTrue(resultsPage.doesResultLabelIncludeKeyword(keyword));

		assertTrue(resultsPage.isResultCountLabelDisplayed());

		resultsPage.clickPage("5"); // Page 5
		assertTrue(resultsPage.isPageNumberHighlighted());

		resultsPage.clickPage("6"); // Page 6
		assertTrue(resultsPage.isPageNumberHighlighted());
	}
	
//  Overview and Details&Specs 	
//  use keyword 	
	@Ignore 
	public void testOverviewDetails() throws InterruptedException
	{
		int numberMultipleItems = 0;
		
		ResultsPage resultsPage = new ResultsPage(driver);

		resultsPage.open(keyword);

		Random randomGenerator = new Random();
		
		ResultItem resultItem;
		DetailItem detailItem;
		String resultItemTitle;

		resultItem = resultsPage.getResult(randomGenerator.nextInt(resultsPage.getCount()));
//		resultItem = resultsPage.getResult(1); /* For test with the 1st result item */
		resultItemTitle = resultItem.getResultItemTitle();
		
		detailItem = resultItem.clickResult();
		assertTrue(detailItem.isTitleSameAsResultTitle(resultItemTitle));

		assertTrue(detailItem.isOverviewTabDispalyed());
		
		System.out.println("\n*****      Overview       *****\n");
				
		int countOverview = detailItem.getCountOverview();
		
		for (int i=1; i <= countOverview; i++)
		{
			if (detailItem.isOverviewSectionDisplayed(i)) /* 'Overview' or 'More Information' */
			{
				assertTrue(detailItem.isInfoOfSectionDisplayed(i));
				
				if (i == 2)
					assertTrue(detailItem.isItemsMulti(i));
			}
		}
		
		detailItem.clickDetailsSpecs(); 

		assertTrue(detailItem.isDetailsSpecsTabDispalyed());
		
		System.out.println("\n*****   Details & Specs   *****\n");
		
		int countDetailsSpecs = detailItem.getCountDetailsSpecs();
		
		for (int i=0; i < countDetailsSpecs; i++)
		{
			if (detailItem.isTheLineFeatureSection(i))
			{
				numberMultipleItems = detailItem.countMultipleItems(i+1, detailItem.getCountDetailsSpecs());

				assertTrue(numberMultipleItems > 0);

				i = i + numberMultipleItems - 1;
			}
		}
	}
}


















