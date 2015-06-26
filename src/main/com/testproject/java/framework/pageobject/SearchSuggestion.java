package com.testproject.java.framework.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.testproject.java.framework.locators.*;

public class SearchSuggestion {

	WebDriver mDriver;

	WebElement mSuggestion;
	
	public SearchSuggestion(WebDriver driver, int i) 
	{
		mDriver = driver;

		WebDriverWait wait = new WebDriverWait(mDriver, 10);
	
		mSuggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + Locators.suggestionsAllLocator + ")[" + i + "]")));
	}

	public WebElement get() 
	{
		return mSuggestion;
	}

	public Boolean isDisplayed() 
	{
		return get().isDisplayed();
	}

	public String getValue() 
	{
		return get().getAttribute("data-query").toUpperCase();
	}
	
	public Boolean isKeywordIncluded(String keyword)
	{
		return (this.getValue().indexOf(keyword.toUpperCase()) >= 0);
	}

	public ResultsPage click() 
	{
		get().click();
		
		ResultsPage resultsPage = new ResultsPage(mDriver);
		
		return resultsPage;
	}

}
