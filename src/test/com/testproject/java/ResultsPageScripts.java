package com.testproject.java;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
import com.testproject.java.framework.pageobject.PageElement;
import com.testproject.java.framework.pageobject.ResultItem;
import com.testproject.java.framework.pageobject.ResultsPage;
import com.testproject.java.framework.pageobject.SortBox;

@RunWith(value = Parameterized.class)
public class ResultsPageScripts {

	static WebDriver driver;

	String keyword = "Galaxy";
//	String keyword = "Galaxy S6";
//	String keyword = "iPhone";
//	String keyword = "iPhone 6";

	private String inputData;

//CSV input	
//	private static String filePath = "D:\\DT\\bestbuy2.txt"; 		/* SORT- testProductSummary() 		*/
//	private static String filePath = "D:\\DT\\bestbuy33.txt"; 		/* SORT-testProducSort() 			*/ 
	private static String filePath = "D:\\DT\\bestbuy34.txt"; 		/* SORT-testProducSort() 			*/
//	private static String filePath = "D:\\DT\\bestbuynull.txt";    /* NAVIGATE Page-testPageNavigate() */ 
		
	public ResultsPageScripts(String inputData) 
	{
		this.inputData = inputData;
	}
/*
	@Parameters
	public static Collection<Object[]> data() throws IOException
	{
		CSVFile csvFile = new CSVFile(filePath);
	    
		Object[][] data = csvFile.getData();
		
		return Arrays.asList(data);
	}
*/
	
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
		ResultsPage resultsPage = new HomePage(driver).search(keyword);
		
		assertTrue(resultsPage.getResultLabel().indexOf(keyword) >= 0);

		assertTrue(resultsPage.visibilityResultCountLabel());

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
	@Ignore
	public void testSortByPriceDescending() throws InterruptedException 
	{
		String[] inputSortKey = inputData.split("\\s*-\\s*");

		keyword = inputSortKey[0];
		String inputSortKeyNumber = inputSortKey[1];

		ResultsPage resultsPage = new HomePage(driver).search(keyword);
		
		assertTrue(resultsPage.getResultLabel().toLowerCase().indexOf(keyword.toLowerCase())>= 0);
		
		assertTrue(resultsPage.visibilityResultCountLabel());
		
		new SortBox(driver).changeSortOrder(inputSortKeyNumber);
		
		Thread.sleep(2000);
		
		for (int i = 2; i <= resultsPage.getCount(); i++) {

			Double prePrice = resultsPage.getResult(i-1).getPrice();
			Double currPrice = resultsPage.getResult(i).getPrice();
			
			assertTrue(prePrice >= currPrice);
		}
}

//  Page Navigation	
	@Test
	public void testPageNavigate() throws InterruptedException
	{
		ResultsPage resultsPage = new HomePage(driver).search(keyword);

		assertTrue(resultsPage.getResultLabel().indexOf(keyword) >= 0);

		assertTrue(resultsPage.visibilityResultCountLabel());

		PageElement pageElement = new PageElement(driver);
		
		pageElement.clickPage("5"); 
		assertTrue(pageElement.getHighlightedPage() == 5);

		pageElement.clickPage("6"); 
		assertTrue(pageElement.getHighlightedPage() == 6);
	}
	
//  Overview tab 	
	@Ignore
	public void testOverviewDetails() throws InterruptedException
	{
		ResultsPage resultsPage = new HomePage(driver).search(keyword);

		ResultItem resultItem = resultsPage.getResult(1); 

		String titleResultItem = resultItem.getTitle();
		
		DetailPage detailPage = resultItem.clickResult();
		
		assertEquals(detailPage.getTitle(), titleResultItem);

		assertTrue(detailPage.visibilityOverviewTab());
		
		for (int i=1; i <= detailPage.getCountOverview(); i++)
		{
			if (detailPage.isOverviewSectionDisplayed(i)) /* 'Overview' or 'More Information' */
			{
				assertTrue(detailPage.isInfoOfSectionDisplayed(i));
				
				if (i == 2)
					assertTrue(detailPage.isItemsMulti(i));
			}
		}
	}
	
//  Details & Specs tab 	
	@Ignore
	public void testDetailsSpecsTab() throws InterruptedException
	{
		ResultsPage resultsPage = new HomePage(driver).search(keyword);

		ResultItem resultItem = resultsPage.getResult(1); 

		String titleResultItem = resultItem.getTitle();
		
		DetailPage detailPage = resultItem.clickResult();

		assertEquals(detailPage.getTitle(), titleResultItem);
		
		detailPage.clickDetailsSpecs(); 

		assertTrue(detailPage.visibilityDetailsSpecsTab());
		
		int countDetailsSpecs = detailPage.getCountDetailsSpecs();
		int numberMultipleItems = 0;		

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


















