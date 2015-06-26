package com.testproject.java.framework.pageobject;
//
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.testproject.java.framework.locators.*;

public class ResultItem {

	WebDriver mDriver;

	int mResultIndex;
	static int mResultIndexOnSale;

	String resultPriceLocatorStr;
	String resultImageLocatorStr;
	String resultItemTitleLocatorStr;

	public ResultItem(WebDriver driver, int resultIndex) {
		mDriver = driver;

		mResultIndex = resultIndex;
		if (resultIndex == 1)
			mResultIndexOnSale = resultIndex;
		else
			mResultIndexOnSale++;			

		resultPriceLocatorStr = Locators.resultPriceLocator + "[" + mResultIndex + "]";
		resultImageLocatorStr = Locators.resultImageLocator + "[" + mResultIndex + "]";
		resultItemTitleLocatorStr = Locators.resultItemTitleLocator + "[" + mResultIndex + "]";
	}

	public String getTitle()
	{
		return mDriver.findElement(By.xpath(resultItemTitleLocatorStr)).getText().replace("Final Clearance", "").trim();
	}
	
	public Boolean doesNameIncludeKeyword(String keyword) {
		String titleValue = getTitle().toUpperCase();

		return titleValue.indexOf(keyword.toUpperCase()) >= 0;
	}

	public String getPriceText() {
		return mDriver.findElement(By.xpath(resultPriceLocatorStr)).getText();
	}
	
	public Boolean isPriceDisplayed() {
		return mDriver.findElement(By.xpath(resultPriceLocatorStr))
				.isDisplayed();
	}

	public Double getPrice() 
	{
		return Double.parseDouble(Pattern.compile("\\$").matcher(getPriceText()).replaceAll(" "));
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
	
	public DetailPage clickResult()
	{
		getImage().click();
		
		return new DetailPage(mDriver);
	}

}
