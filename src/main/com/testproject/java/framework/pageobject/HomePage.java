package com.testproject.java.framework.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testproject.java.framework.locators.*;

public class HomePage {

	WebDriver mDriver;
	String mKeyword;
	String pageUrl = "http://www.bestbuy.ca/";
	
	WebDriverWait wait;

	public HomePage(WebDriver driver) 
	{
		mDriver = driver;

		wait = new WebDriverWait(mDriver, 10);
	}

	public HomePage(WebDriver driver, String keyword) 
	{
		mDriver = driver;
		mKeyword = keyword;

		wait = new WebDriverWait(mDriver, 10);
	}

	public void open() 
	{
		mDriver.get(pageUrl);
	}

	public void setLanguageToEnglish()
	{
		if ((mDriver.findElement(By.xpath(Locators.languageLocator)).getText().equalsIgnoreCase("English")) == true)
		{
			mDriver.findElement(By.xpath(Locators.languageLocator)).click();
		}
	}
	
	public void typeKeyword() 
	{
		getSearchField().click();

		getSearchField().sendKeys(mKeyword);
	}

	public int getSuggestionsCount() 
	{
		return (mDriver.findElements(By.xpath(Locators.suggestionsAllLocator)).size());
	}
	
	public WebElement getSearchField() 
	{
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(Locators.searchKeywordLocator)));
	}

	public WebElement getSearchButton() 
	{
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(Locators.searchButtonLocator)));
	}

	public ResultsPage clickSearchButton() 
	{
		getSearchButton().click();
		ResultsPage resultsPage = new ResultsPage(mDriver);
		return resultsPage;
	}
	
	public SearchSuggestion getSuggestion(WebDriver driver, int i)
	{
		SearchSuggestion searchSuggestion = new SearchSuggestion(driver, i);
		return searchSuggestion;
	}

}




