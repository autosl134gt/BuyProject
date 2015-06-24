package com.testproject.java.framework.pageobject;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testproject.java.framework.locators.Locators;

public class ResultsPage {

	WebDriver mDriver;
		
	By sortSuggestionLocatorStr;
	By pageNumberLocatorStr;
	
	WebElement waitResultsType;
	WebElement waitPageType ;
	WebElement waitPageNumber;
	
	static int mResultsNumber;
	int pageNav;
	int nextPageNav;
	int pageFromGetText;
	int indexHighlightedPage;
	String pageUrl = "http://www.bestbuy.ca/Search/SearchResults.aspx?path=ca77b9b4beca91fe414314b86bb581f8en20&query=";
	
	Boolean bl;
	
	WebDriverWait wait;

	public ResultsPage(WebDriver driver) {
		mDriver = driver;
		wait = new WebDriverWait(mDriver, 30);
	}

	public Boolean doesResultLabelIncludeKeyword(String keyword) {

		return wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(Locators.resultForLabelLocator)))
						.getText().trim().toUpperCase().indexOf(keyword.toUpperCase()) >= 0;
	}

	public String getResultLabel() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.resultForLabelLocator))).getText();
	}
	
	public String getCountLabel() {
		return wait.until(
				ExpectedConditions
						.visibilityOfElementLocated(By.xpath(Locators.resultCountLabelLocator))).getText();
	}

	public boolean isResultCountLabelDisplayed() {
		
		String[] resultNumber = getCountLabel().split("\\s*-\\s*");

		return (Pattern.compile(".*-.*", Pattern.CASE_INSENSITIVE).matcher(
				getCountLabel()).matches())
				&& (Pattern.compile(".*of.*", Pattern.CASE_INSENSITIVE)
						.matcher(getCountLabel()).matches())
				&& (Integer.parseInt(resultNumber[0]) > 0);
	}

	public int getCount() {
		System.out.println("xx");
		return mDriver.findElements(By.xpath(Locators.keywordResultTitlesLocator)).size();
	}

	public ResultItem getResult(int i) {
		ResultItem result = new ResultItem(mDriver, i);

		return result;
	}

	public WebElement getSortBox()
	{
		WebElement mSortBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.sortBoxLocator)));

		return mSortBox;
	}
	
	public WebElement getSuggestion(String inputSortKeyNumber)
	{
		sortSuggestionLocatorStr = By.xpath(Locators.sortSuggestionLocator + "[" + inputSortKeyNumber + "]");

		wait.until(ExpectedConditions.presenceOfElementLocated(sortSuggestionLocatorStr));
		
		WebElement mSuggestion = mDriver.findElement(sortSuggestionLocatorStr);
	
		return mSuggestion;
	}
	
	public void changeSortOrder(String inputSortKeyNumber) throws InterruptedException
	{
		getSortBox().click();
		
		getSuggestion(inputSortKeyNumber).click();
	}
	
	public int getHighlightedPageIndex()
	{
		int i = 0;
		bl = true;
		
		do
		{
			i++;
		
			pageNumberLocatorStr = By.xpath(Locators.pageTypeLocator1 + "[" + i + "]");

			waitPageNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(pageNumberLocatorStr));
	
			if (Pattern.compile(".*current.*", Pattern.CASE_INSENSITIVE)
					.matcher(waitPageNumber.getAttribute("class")).matches())
			{
				bl = false;
				
				break;
			}
		} while (bl);
	
		return i;
	}
	
	public WebElement getNextPage()
	{
		pageNav = mDriver.findElements(By.xpath(Locators.pageTypeLocatorAll)).size();

		pageNumberLocatorStr = By.xpath(Locators.pageTypeLocator2 + "[" + pageNav + "]");
		
		waitPageNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(pageNumberLocatorStr));

		nextPageNav = Integer.parseInt(waitPageNumber.getAttribute("data-page"));
		
		return waitPageNumber;
	}
	
	public WebElement getPreviousPage()
	{
		pageNav = 1;

		pageNumberLocatorStr = By.xpath(Locators.pageTypeLocator2 + "[" + pageNav + "]");
		
		waitPageNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(pageNumberLocatorStr));

		nextPageNav = Integer.parseInt(waitPageNumber.getAttribute("data-page"));
		
		return waitPageNumber;
	}
	
	public WebElement getPage(String pageNavStr)
	{
		nextPageNav = Integer.parseInt(pageNavStr);
	
		for (int i = 2; i < mDriver.findElements(By.xpath(Locators.pageTypeLocatorAll)).size(); i++)
		{
			pageNumberLocatorStr = By.xpath(Locators.pageTypeLocator2 + "[" + i + "]");

			waitPageNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(pageNumberLocatorStr));

			if (isNumeric(waitPageNumber.getAttribute("data-page")))
			{	
				pageFromGetText = Integer.parseInt(waitPageNumber.getAttribute("data-page"));

				if (nextPageNav == pageFromGetText)
					break;
			}
		}
		
		return waitPageNumber;
	}
	
	public ResultsPage clickPage(String pageNavStr)
	{
		indexHighlightedPage = getHighlightedPageIndex();
	
		if (pageNavStr.equalsIgnoreCase("Next"))
			getNextPage().click();
		else
			if (pageNavStr.equalsIgnoreCase("Prev"))
				getPreviousPage().click();
			else
				getPage(pageNavStr).click();
		
		ResultsPage resultsPage = new ResultsPage(mDriver);
		
		return resultsPage;
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public Boolean verifyClickedPageNumber()
	{
		bl = true;
		
		pageNumberLocatorStr = By.xpath(Locators.pageTypeLocator2 + "[" + indexHighlightedPage + "]");

		waitPageNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(pageNumberLocatorStr));

		if (isNumeric(waitPageNumber.getAttribute("data-page")))
		{	
			pageFromGetText = Integer.parseInt(waitPageNumber.getAttribute("data-page"));

			if (nextPageNav == pageFromGetText)
				bl = true;
			else
				bl = false;
		}
		else
			bl = false;

		return bl;
	}
	
	public Boolean isPageNumberIncludedInUrl()
	{
		return (Pattern.compile(".*" + nextPageNav + ".*",
				Pattern.CASE_INSENSITIVE).matcher(mDriver.getCurrentUrl()).matches());
	}
	
	public Boolean isPageNumberHighlighted()
	{
		indexHighlightedPage = getHighlightedPageIndex();
		
		bl = verifyClickedPageNumber();

		if (bl)
			bl = isPageNumberIncludedInUrl();

		if (bl)
			bl = isResultCountLabelDisplayed();

		return bl;
	}
	
	public Integer getHighlightedPage()
	{
		indexHighlightedPage = getHighlightedPageIndex();
		
		if (verifyClickedPageNumber())
			return pageFromGetText;
		else 
			return 999;		// Error
	}
	
	public void open(String keyword)
	{
		pageUrl += keyword;
		mDriver.get(pageUrl);
	}

}


















