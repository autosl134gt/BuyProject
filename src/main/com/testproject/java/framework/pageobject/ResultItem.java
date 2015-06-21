package com.testproject.java.framework.pageobject;

import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.testproject.java.framework.locators.*;

public class ResultItem {

	WebDriver mDriver;

	int mResultIndex;
	static int mResultIndexOnSale;

	String keywordResultTitleLocatorStr;
	String resultPriceLocatorStr;
	String resultImageLocatorStr;
	String resultItemTitleLocatorStr;
	String resultsUrl;

	public ResultItem(WebDriver driver, int resultIndex) {
		mDriver = driver;

		mResultIndex = resultIndex;
		if (resultIndex == 1)
			mResultIndexOnSale = resultIndex;
		else
			mResultIndexOnSale++;			

		keywordResultTitleLocatorStr = Locators.keywordResultTitleLocator + "["
				+ mResultIndex + "]";
		resultPriceLocatorStr = Locators.resultPriceLocator + "[" + mResultIndex + "]";
		resultImageLocatorStr = Locators.resultImageLocator + "[" + mResultIndex + "]";
		resultItemTitleLocatorStr = Locators.resultItemTitleLocator + "[" + mResultIndex + "]";
	}

	public WebElement getTitle() {
		return mDriver.findElement(By.xpath(keywordResultTitleLocatorStr));
	}
	
	public String getName() {
		return getTitle().getText();
	}

	public Boolean doesNameIncludeKeyword(String keyword) {
		String titleValue = getName().toUpperCase();

		return titleValue.indexOf(keyword.toUpperCase()) >= 0;
	}

	public String getPrice() {
		return mDriver.findElement(By.xpath(resultPriceLocatorStr)).getText();
	}
	
	public Boolean isPriceDisplayed() {
		return mDriver.findElement(By.xpath(resultPriceLocatorStr))
				.isDisplayed();
	}

	public Boolean isPriceValid() {
		return Double.parseDouble(Pattern.compile("\\$").matcher(getPrice())
				.replaceAll(" ")) >= 0;
	}

	public Boolean isResultItemOnSale() throws InterruptedException 
	{
		String resultPriceDivLocatorStr;
		
		resultPriceDivLocatorStr = Locators.resultPriceDivLocator2 + "[" + mResultIndexOnSale + "]";
			
		WebElement webElementOnSale = mDriver.findElement(By.xpath(resultPriceDivLocatorStr));

		Boolean bl = webElementOnSale.getAttribute("class").toString().matches("prodprice price-onsale");
		
		if (bl)
			mResultIndexOnSale += 2;
					
		return bl;
	}
	
	public String getResultItemTitle()
	{
		System.out.println("Product: " + mDriver.findElement(By.xpath(resultItemTitleLocatorStr)).getText());
		
		return mDriver.findElement(By.xpath(resultItemTitleLocatorStr)).getText();
	}
	
	public WebElement getImage() {
		return mDriver.findElement(By.xpath(resultImageLocatorStr));
	}

	public Boolean isImageDisplayed() {
		return getImage().isDisplayed();
	}

	public Boolean isImageUrlValid() {
		String srcValue = getImage().getAttribute("src");

		return (Pattern.compile(".*http://www.bestbuy.ca/.*",
				Pattern.CASE_INSENSITIVE).matcher(srcValue).matches())
				&& (Pattern.compile(".*jpg.*", Pattern.CASE_INSENSITIVE)
						.matcher(srcValue).matches());
	}
	
	public void displayResultsPage()
	{
		mDriver.get(resultsUrl); 		
	}
	
	public DetailItem clickResult()
	{
		resultsUrl = mDriver.getCurrentUrl();
		
		getImage().click();
		
		return new DetailItem(mDriver);
	}

}
