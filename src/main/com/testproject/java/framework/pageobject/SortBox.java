package com.testproject.java.framework.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testproject.java.framework.locators.Locators;

public class SortBox {

	WebDriver mDriver;
		
	By pageNumberLocatorStr;
	
	WebElement waitPageNumber;
	
	int pageNav;
	int nextPageNav;
	int pageFromGetText;
	int indexHighlightedPage;
	
	Boolean bl;
	
	WebDriverWait wait;

	public SortBox (WebDriver driver) {
		mDriver = driver;
		wait = new WebDriverWait(mDriver, 10);
	}

	public WebElement getSortBox()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.sortBoxLocator)));
	}
	
	public WebElement getSortValue(String inputSortKeyNumber)
	{
		By sortSuggestionLocatorStr = By.xpath(Locators.sortSuggestionLocator + "[" + inputSortKeyNumber + "]");

		return mDriver.findElement(sortSuggestionLocatorStr);
	}
	
	public void changeSortOrder(String inputSortKeyNumber) throws InterruptedException
	{
		getSortBox().click();
		
		getSortValue(inputSortKeyNumber).click();
	}
	
}


