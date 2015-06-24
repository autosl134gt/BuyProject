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
import com.testproject.java.framework.pageobject.DetailPage;
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
//	private static String filePath = "D:\\DT\\bestbuy2.txt"; 		/* SORT- testProductSummary() 		*/
//	private static String filePath = "D:\\DT\\bestbuy33.txt"; 		/* SORT-testProducSort() 			*/ 
	private static String filePath = "D:\\DT\\bestbuy34.txt"; 		/* SORT-testProducSort() 			*/
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
		ResultsPage resultsPage = new HomePage(driver, keyword).search();
		
		assertTrue(resultsPage.getResultLabel().indexOf(keyword) >= 0);

		assertTrue(resultsPage.isResultCountLabelDisplayed());

		ResultItem resultItem;

		int countResultsPage = resultsPage.getCount();
		
		for (int i = 1; i <= countResultsPage; i++) {
			resultItem = resultsPage.getResult(i);

			assertTrue(resultItem.isImageDisplayed());
			assertTrue(resultItem.isImageUrlValid());
			assertTrue(resultItem.isPriceDisplayed());
			assertTrue(resultItem.getPrice() >= 0);

			assertTrue(resultItem.doesNameIncludeKeyword(keyword));
		}
	}
	
//	SORT Price: High to Low
//	Input file: bestbuy34.txt
	@Test
	public void testSortByPriceDescending() throws InterruptedException 
	{
		String[] inputSortKey = inputData.split("\\s*-\\s*");

		keyword = inputSortKey[0];
		String inputSortKeyNumber = inputSortKey[1];

		ResultsPage resultsPage = new HomePage(driver, keyword).search();
		
		assertTrue(resultsPage.getResultLabel().toLowerCase().indexOf(keyword.toLowerCase())>= 0);
		
		assertTrue(resultsPage.isResultCountLabelDisplayed());

		resultsPage.changeSortOrder(inputSortKeyNumber);
		
		Thread.sleep(2000);
		
		for (int i = 2; i <= resultsPage.getCount(); i++) {

			Double prePrice = resultsPage.getResult(i-1).getPrice();
			Double currPrice = resultsPage.getResult(i).getPrice();
			
			assertTrue(prePrice >= currPrice);
		}
}

//  Page Navigation	
	@Ignore
	public void testPageNavigate() throws InterruptedException
	{
		ResultsPage resultsPage = new HomePage(driver, keyword).search();

		assertTrue(resultsPage.getResultLabel().indexOf(keyword) >= 0);

		assertTrue(resultsPage.isResultCountLabelDisplayed());

		resultsPage.clickPage("5"); // Page 5
		assertTrue(resultsPage.getHighlightedPage() == 5);

		resultsPage.clickPage("6"); // Page 6
		assertTrue(resultsPage.getHighlightedPage() == 6);
	}
	
//  Overview and Details&Specs 	
	@Ignore
	public void testOverviewDetails() throws InterruptedException
	{
		int numberMultipleItems = 0;
		
		ResultsPage resultsPage = new ResultsPage(driver);

		resultsPage.open(keyword);

		Random randomGenerator = new Random();
		
		ResultItem resultItem;
		DetailPage detailPage;

		resultItem = resultsPage.getResult(randomGenerator.nextInt(resultsPage.getCount()));
//		resultItem = resultsPage.getResult(1); /* For test with the 1st result item */
		
		detailPage = resultItem.clickResult();
		assertEquals(detailPage.getTitle(), resultItem.getTitle());

		assertTrue(detailPage.isOverviewTabDispalyed());
		
		System.out.println("\n*****      Overview       *****\n");
				
		int countOverview = detailPage.getCountOverview();
		
		for (int i=1; i <= countOverview; i++)
		{
			if (detailPage.isOverviewSectionDisplayed(i)) /* 'Overview' or 'More Information' */
			{
				assertTrue(detailPage.isInfoOfSectionDisplayed(i));
				
				if (i == 2)
					assertTrue(detailPage.isItemsMulti(i));
			}
		}
		
		detailPage.clickDetailsSpecs(); 

		assertTrue(detailPage.isDetailsSpecsTabDispalyed());
		
		System.out.println("\n*****   Details & Specs   *****\n");
		
		int countDetailsSpecs = detailPage.getCountDetailsSpecs();
		
		for (int i=0; i < countDetailsSpecs; i++)
		{
			if (detailPage.isTheLineFeatureSection(i))
			{
				numberMultipleItems = detailPage.countMultipleItems(i+1, detailPage.getCountDetailsSpecs());

				assertTrue(numberMultipleItems > 0);

				i = i + numberMultipleItems - 1;
			}
		}
	}
}


















