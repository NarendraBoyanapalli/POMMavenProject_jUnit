package com.gmail.qa.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gmail.qa.base.Base;
import com.gmail.qa.utils.DataProvider;

public class InboxPage extends Base 
{
	JavascriptExecutor js= (JavascriptExecutor) wd;
	Logger logger= Logger.getLogger(this.getClass().getSimpleName());
	
	//Page Elements
		@FindBy(xpath="//span[@class='gb_bb gbii']")
		WebElement profileIcon;
		
		@FindBy(xpath="//a[@class='gb_Aa gb_lg gb_sg gb_4e gb_Jb']")
		WebElement signOutButton;
		
		@FindBy(xpath="//div[@role='button' and contains(text(),'Compose')]")
		WebElement composeButton;
		
		@FindBy(xpath="//textarea[@name='to']")
		WebElement to;
		
		@FindBy(xpath="//input[@name='subjectbox']")
		WebElement subject;
		
		@FindBy(xpath="//div[@class='J-J5-Ji btA']")
		WebElement sendButton;
		
		@FindBy(xpath="//a[contains(text(),'Sent')]")
		WebElement sentButton;
		
		@FindBy(xpath="//a[contains(text(),'Inbox')]")
		WebElement inboxButton;
		
		@FindBy(xpath="//div[@class='y6']/span/span[contains(text(),'Test Automation')]")
		List<WebElement> testAutomationMails;
		
		@FindBy(xpath="//*[@id=':5']/div[2]/div/div/div[2]/div[3]")
		WebElement deleteButton;
		
		@FindBy(xpath="//span[@class='asa bjy']")
		WebElement moreButton;
		
		@FindBy(xpath="//div[@class='VBgE5b W0PX5c LM']")
		WebElement hangOutIcon;
		
		//Initialize Page Elements;
		public  InboxPage(WebDriver wd)
		{
			super.wd=wd;
			PageFactory.initElements(wd, this);
		}
		
		//Method to wait for unique elements of the page to load
		public void waitForPageLoad()
		{
			List<WebElement> elementsList= getUniqueElements();
			for(WebElement element: elementsList)
			{
				waitForElement(wd, element);
			}
		}
		
		public List<WebElement> getUniqueElements()
		{
			List<WebElement> elementsList=  new ArrayList<WebElement>();
			elementsList.add(profileIcon);
			elementsList.add(composeButton);
			
			return elementsList;
		}
		
		public InboxPage clickProfileIcon() throws InterruptedException
		{
			waitForPageLoad();
			profileIcon.click();
			return this;
		}
		
		public InboxPage composeMail() throws IOException, InterruptedException
		{
			waitForPageLoad();
			try
			{
				composeButton.click();
				to.sendKeys(DataProvider.getValue("to"));
				subject.sendKeys(DataProvider.getValue("subject"));
				Thread.sleep(3000);
				sendButton.click();
			}
			catch(NoSuchElementException e)
			{
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			catch(ElementNotFoundException e)
			{
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			catch(NullPointerException e)
			{
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			return this;
		}
		
		public InboxPage deleteMail() throws InterruptedException, TimeoutException
		{
			waitForPageLoad();
			try
			{
				inboxButton.click();
				for(WebElement testAutomationMail: testAutomationMails)
				{
					testAutomationMail.click();
					//wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Thread.sleep(3000);
					deleteButton.click();
				}
			}
			catch(ElementNotFoundException e)
			{
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			return this;
		}
		
		public boolean isMailDisplayed() throws InterruptedException, TimeoutException
		{
			//wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			Thread.sleep(2000);
			try
			{
				sentButton.click();
			}
			catch(StaleElementReferenceException e)
			{
				
			}
			boolean indicator=false;
			if(!testAutomationMails.isEmpty())
			{
				for(WebElement testAutomationMail: testAutomationMails)
				{
					indicator=testAutomationMail.isDisplayed();
					break;
				}
			}
			return indicator;
		}
		
		public EnterPasswordPage clickSignOut() throws InterruptedException
		{
			signOutButton.click();
			try
			{
				wd.switchTo().alert().accept();
			}
			catch(NoAlertPresentException e)
			{
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			return new EnterPasswordPage(wd);
		}

		@Override
		public String getDataPath() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

}
