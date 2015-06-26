package com.testproject.java.framework.pageobject;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testproject.java.framework.locators.Locators;

public class PageElement {

	WebDriver mDriver;
		
	By pageNumberLocatorStr;
	
	WebElement waitPageNumber;
	
	int pageNav;
	int nextPageNav;
	int pageFromGetText;
	int indexHighlightedPage;
	
	Boolean bl;
	
	WebDriverWait wait;

	public PageElement(WebDriver driver) {
		mDriver = driver;
		wait = new WebDriverWait(mDriver, 10);
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
	
	public Integer getHighlightedPage()
	{
		indexHighlightedPage = getHighlightedPageIndex();
		
		if (verifyClickedPageNumber())
			return pageFromGetText;
		else 
			return 999;		// Error
	}

}






