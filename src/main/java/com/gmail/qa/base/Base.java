package com.gmail.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gmail.qa.pages.EnterEmailPage;
import com.gmail.qa.utils.DataProvider;
import com.gmail.qa.utils.Utils;

public abstract class Base 
{
	public WebDriver wd;
	public static Properties properties;
	public static HashMap<String, String> map;
	Logger logger;
	Utils utils;
	
	//Constructor to initialize and load config.properties.
	public Base()
	{
		logger= Logger.getLogger(this.getClass().getSimpleName());
		logger.info("Execution Started");
		try
		{
			properties = new Properties();
			FileInputStream input = new FileInputStream("C:\\Let's Code!\\POMMavenProject_jUnit\\src\\main\\java\\com\\gmail\\qa\\config\\config.properties");
			properties.load(input);
		}
		catch (FileNotFoundException e)
		{
			logger.info(e.getStackTrace());
		} 
		catch (IOException e) 
		{
			logger.info(e.getStackTrace());
		}
	}

	//Method to choose browser, initialize WedDriver accordingly and launch URL, get test data from excel.
	public EnterEmailPage setup() throws IOException
	{
		String browserName = properties.getProperty("browser");
		if (browserName.equals("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver","C:\\00 SeleniumJars\\chromedriver_win32\\chromedriver.exe");
			wd=new ChromeDriver();
		}
		else if (browserName.equals("Firefox"))
		{
			wd=new FirefoxDriver();;
		}
		else if (browserName.equals("ie"))
		{
			System.setProperty("webdriver.ie.driver","C:\\00 SeleniumJars\\chromedriver_win32\\internetexplorerdriver.exe");
			wd=new InternetExplorerDriver();
		}
		
		wd.manage().window().maximize();
		wd.manage().deleteAllCookies();
		//wd.manage().timeouts().pageLoadTimeout(Utils.PAGE_LOAD_TIMEOUT,  TimeUnit.SECONDS);
		//wd.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		wd.get(properties.getProperty("url"));
		utils= new Utils();
		utils.getURL();
		
		map= DataProvider.getTestData(getDataPath());
	
		return new EnterEmailPage(wd);
	}
	
	//Method to end browser, WebDriver session
	public void tearDown()
	{
		wd.close();
		wd.quit();
		logger.info("Execution Ended");
	}
	
	public abstract String getDataPath() throws IOException; 
	
	public void waitForElement(WebDriver wd, WebElement element)
	{
		if(wd==null)
			throw new IllegalArgumentException("WebDriver cannot be NULL");
		else
		{
			WebDriverWait wait= new WebDriverWait(wd, 20);
			wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(element), ExpectedConditions.elementToBeClickable(element)));
		}
	}
}
