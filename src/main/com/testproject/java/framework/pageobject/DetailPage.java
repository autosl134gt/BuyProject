package com.testproject.java.framework.pageobject;
//
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.regex.Pattern;

import com.testproject.java.framework.locators.*;

public class DetailPage { 
	
	WebDriver mDriver;
	WebElement detailOverviewSection;

	String srcValue;
	String detailOverviewItemLocatorStr; 
	String detailAttrKeyLocatorStr;
	String detailAttrValueLocatorStr;
	String nameSection;
	
	int lengthSectionSubInfo;
	int lengthSection;
	
	public DetailPage (WebDriver driver)
	{
		mDriver = driver;
	}
	
	public WebElement getImage()  
	{                                       
		return mDriver.findElement(By.xpath(Locators.detailImageLocator));
	}
	
	public WebElement getImage2()
	{
		return mDriver.findElement(By.xpath(Locators.detailImageLocator2));
	}
	
	public Boolean isImageDisplayed() throws InterruptedException 
	{
		Boolean bl = false;
	
		try
		{
			bl = getImage().isDisplayed();

			srcValue = getImage().getAttribute("src");
		}
		catch (Exception e)
		{
			bl = getImage2().isDisplayed(); 

			srcValue = getImage2().getAttribute("src");
		}
	
		return bl;
	}
	
	public Boolean isImageUrlValid()
	{
		return (Pattern.compile(".*http://www.bestbuy.ca/.*", Pattern.CASE_INSENSITIVE).matcher(srcValue).matches()) && (Pattern.compile(".*jpg.*", Pattern.CASE_INSENSITIVE).matcher(srcValue).matches());
	}
	
	public Double getPrice()
	{
		return Double.parseDouble(Pattern.compile("\\$").matcher(mDriver.findElement(By.xpath(Locators.detailPriceLocator)).getText()).replaceAll(" "));	
	}
	
	public Boolean isOnlinePurchaseInfoDisplayed()
	{
		return mDriver.findElement(By.xpath(Locators.onlinePurchaseLocator)).isDisplayed();
	}
	
	public Boolean isInStorPurchaseInfoDisplayed()
	{
		return mDriver.findElement(By.xpath(Locators.inStorePurchaseLocatoer)).isDisplayed();
	}

	public Boolean isWishListDisplayed()
	{
		return mDriver.findElement(By.xpath(Locators.wishListLocator)).isDisplayed();
	}

	public String getTitle()
	{
		return mDriver.findElement(By.xpath(Locators.detailItemTitleLocator)).getText().replace("Final Clearance", "").trim();
	}
	
	public Boolean visibilityOverviewTab()
	{
		return (mDriver.findElement(By.xpath(Locators.detailOverviewTabLocator))).isDisplayed();
	}
	
	public Integer getCountOverview()
	{
		return mDriver.findElements(By.xpath(Locators.detailOverviewItemLocator)).size();
	}
		
	public Boolean isOverviewSectionDisplayed(int i)
	{		
		detailOverviewItemLocatorStr = Locators.detailOverviewItemLocator + "[" + i + "]"; 
		
		lengthSectionSubInfo = mDriver.findElement(By.xpath(detailOverviewItemLocatorStr)).getText().length();
		
		if (lengthSectionSubInfo > 0)
		{
			detailOverviewItemLocatorStr = Locators.detailOverviewItemLocator + "[" + i + "]/h4"; 
			
			detailOverviewSection = mDriver.findElement(By.xpath(detailOverviewItemLocatorStr));
			
			lengthSection = detailOverviewSection.getText().length();
			
			return detailOverviewSection.isDisplayed();
		}
		else
			return false;
	}
	
	public Boolean isInfoOfSectionDisplayed(int i)
	{
		return (lengthSectionSubInfo > (lengthSection + 1));
	}
	
	public Boolean isItemsMulti(int i)
	{
		return mDriver.findElements(By.xpath(Locators.detailOverviewMultiItemLocator)).size() > 0;
	}
	
	public void clickDetailsSpecs()
	{
		mDriver.findElement(By.xpath("//li[@class='ui-tab']")).click();
	}
	
	public Boolean visibilityDetailsSpecsTab()
	{
		return (mDriver.findElement(By.xpath(Locators.detailDetailsSpecsTabLocator))).isDisplayed();
	}
	
	public Integer getCountDetailsSpecs()
	{
		return mDriver.findElements(By.xpath(Locators.detailAttrKeyLocator)).size();
	}
	
	public Boolean isTheLineFeatureSection(int i)
	{		
		nameSection = mDriver.findElements(By.xpath(Locators.detailAttrKeyLocator)).get(i).getText();

		return 	(mDriver.findElements(By.xpath(Locators.detailAttrKeyLocator)).get(i).getText().length() > 0)
				&&
				(mDriver.findElements(By.xpath(Locators.detailAttrValueLocator)).get(i).getText().length() == 0);
	}
	
	public Integer countMultipleItems(int indexStart, int indexTotal)
	{
		int numberMultipleItems = 0;
		
		for (int i = indexStart; i < indexTotal; i++)
		{
			if ((mDriver.findElements(By.xpath(Locators.detailAttrKeyLocator)).get(i).getText().length() > 0)
				&&
				(mDriver.findElements(By.xpath(Locators.detailAttrValueLocator)).get(i).getText().length() > 0))
				numberMultipleItems++;
			else
				break;
		}

		return numberMultipleItems;
	}
}










