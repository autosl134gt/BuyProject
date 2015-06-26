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
		
	By pageNumberLocatorStr;
	
	WebElement waitPageNumber;
	
	int pageNav;
	int nextPageNav;
	int pageFromGetText;
	int indexHighlightedPage;
	
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

	public boolean visibilityResultCountLabel() {
		
		String[] resultNumber = getCountLabel().split("\\s*-\\s*");
System.out.println("getCountLabel()=" + getCountLabel() + " resultNumber[0]=" + resultNumber[0]);
		return (Pattern.compile(".*-.*", Pattern.CASE_INSENSITIVE).matcher(
				getCountLabel()).matches())
				&& (Pattern.compile(".*of.*", Pattern.CASE_INSENSITIVE)
						.matcher(getCountLabel()).matches())
				&& (Integer.parseInt(resultNumber[0]) > 0);
	}

	public int getCount() {
		return mDriver.findElements(By.xpath(Locators.keywordResultTitlesLocator)).size();
	}

	public ResultItem getResult(int i) {
		return new ResultItem(mDriver, i);
	}

}


















