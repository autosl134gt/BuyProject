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
import com.testproject.java.framework.pageobject.HomePage;
import com.testproject.java.framework.pageobject.ResultItem;
import com.testproject.java.framework.pageobject.ResultsPage;
import com.testproject.java.framework.pageobject.SearchSuggestion;

@RunWith(value = Parameterized.class)
public class HomePageScripts {

	static WebDriver driver;

//	String keyword = "Galaxy";
//	String keyword = "iPhone";
	private String keyword;
	String suggestionValue;
	
	private static String filePath = "D:\\DT\\bestbuy.txt";
	
//	public HomePageScripts(String keyword)
//	{
//		this.keyword = keyword;
//	}
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
	public void testSuggestionsCorrect() throws InterruptedException 
	{
		
		HomePage homePage = new HomePage(driver);

		homePage.open();

		homePage.typeKeyword(keyword);

		SearchSuggestion searchSuggestion = homePage.getSuggestion(driver, 5);

		assertTrue(searchSuggestion.isDisplayed());
		
		assertTrue(searchSuggestion.isKeywordIncluded(keyword));
		
		assertTrue(homePage.getSuggestionsCount() > 0);

		ResultsPage resultsPage = searchSuggestion.click();

		assertTrue(resultsPage.doesResultLabelIncludeKeyword(keyword));

		ResultItem resultItem;

		for (int i = 1; i <= resultsPage.getCount(); i++) 
		{
			resultItem = resultsPage.getResult(i);

			assertTrue(resultItem.doesNameIncludeKeyword(keyword));
		}
	}

	@Ignore
	public void testSearchNoKeyword() throws InterruptedException 
	{
		HomePage homePage = new HomePage(driver);

		homePage.open();

		homePage.clickSearchButton();

		ResultsPage resultsPage = new ResultsPage(driver);

		assertTrue(resultsPage.getCount() == 32);
	}

	@Test
	public void testSearchWithKeyword() throws InterruptedException 
	{
		ResultsPage resultsPage = new HomePage(driver).search(keyword);

		assertTrue(resultsPage.doesResultLabelIncludeKeyword(keyword));

		ResultItem resultItem;

		for (int i = 1; i <= resultsPage.getCount(); i++) 
		{
			resultItem = resultsPage.getResult(i);

			assertTrue(resultItem.doesNameIncludeKeyword(keyword));
		}
	}

}

