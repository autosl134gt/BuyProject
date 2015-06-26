package com.testproject.java.framework.locators;
//
public abstract class Locators {

//DetailItem
	public static String detailImageLocator = "(//ul[@class='ui-carousel-item-list']//li//img)[1]";
	public static String detailImageLocator2 = "(//div[@id='pdp-gallery']//img)";	
	public static String detailPriceLocator = "(//div[@class='price-module clearfix']//div[@itemprop='price'])";
	public static String onlinePurchaseLocator = "(//div[@class='availability-section']//p[@class='availability-header'])";
	public static String inStorePurchaseLocatoer = "(//div[@class='availability-section in-store']//p[@class='availability-header'])";
	public static String wishListLocator = "(//div[@class='qty-limit availability-section']//a)[1]";
	public static String detailItemTitleLocator = "(//h1[@class='product-title'])";
	public static String detailOverviewTabLocator = "//li[@class='ui-tab active']";
	public static String detailOverviewItemLocator = "//div[@class='tab-overview-item']";
	public static String detailOverviewMultiItemLocator = "//div[@class='tab-overview-item']/div/ul/li";
	public static String detailDetailsSpecsTabLocator = "//li[@class='ui-tab']";
	public static String detailAttrKeyLocator = "//ul[@class='std-tablist']/li//span[@class='attribute-key span5']";
	public static String detailAttrValueLocator = "//ul[@class='std-tablist']/li//div[@class='attribute-value span7']";
	
// HomePage
	public static String searchKeywordLocator = "//input[@id='ctl00_MasterHeader_ctl00_uchead_GlobalSearchUC_TxtSearchKeyword']";
	public static String searchButtonLocator = "//a[@id='ctl00_MasterHeader_ctl00_uchead_GlobalSearchUC_BtnSubmitSearch']";
	public static String languageLocator = "(//div[@class='hdr-toolbar clearfix']//li[@class])[1]/a";
	
//ResultsItem
	public static String resultPriceLocator = "(//div[@class='price-wrapper price-medium']//span[@class='amount'])";
	public static String resultImageLocator = "(//div[@class='prod-image']//img)";
	public static String resultPriceDivLocator2 = "(//div[@class='price-wrapper price-medium']//div)";
	public static String resultItemTitleLocator = "(//h4[@class='prod-title'])";
	
//ResultsPage
  	public static String resultForLabelLocator = "//span[@id='search-keyword-result']";
	public static String resultCountLabelLocator = "(//div[@class='display-product-number'])[1]";
	public static String keywordResultTitlesLocator = "//h4[@class='prod-title']/a";
	public static String sortBoxLocator = "//div[@id='ctl00_CC_ProductSearchResultListing_SortByTop']//li[@class='sorting-item']";
	public static String sortSuggestionLocator = "((//li[@class='sorting-item'])[1]/ul/li/a)";
	public static String pageTypeLocatorAll = "(//div[@class='listing-control-wrapper clearfix listing-control-bby listing-control-middle']/div/ul/li//a[@data-page])";
	public static String pageTypeLocator1 = "(//div[@class='listing-control-wrapper clearfix listing-control-bby listing-control-middle']//ul[@class='pagination-control inline-list solr_pagination']/li)";
	public static String pageTypeLocator2 = "(//div[@class='listing-control-wrapper clearfix listing-control-bby listing-control-middle']//div[@class='pagination-control-wrapper text-center']/ul/li/a)";
	
//SearchSuggestion
	public static String suggestionsAllLocator = "//div[@class='widget-search-extend']/ul/li/a";

}
