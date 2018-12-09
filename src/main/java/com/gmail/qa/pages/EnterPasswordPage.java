package com.gmail.qa.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gmail.qa.base.Base;
import com.gmail.qa.exceptions.PasswordNotEnteredException;
import com.gmail.qa.exceptions.PasswordNotMatchedException;
import com.gmail.qa.utils.DataProvider;
import com.gmail.qa.utils.ErrorMessages;
import com.gmail.qa.utils.Utils;

public class EnterPasswordPage extends Base
{
	Logger logger= Logger.getLogger(this.getClass().getSimpleName());
	Utils utils= new Utils();
	
	//Page Elements
		@FindBy(xpath="//input[@type='password']")
		WebElement password;
		
		@FindBy(xpath="//span[@class='RveJvd snByac']")
		WebElement nextButton;
		
		@FindBy(xpath="//content[contains(.,'Narendra Boyanapalli')]")
		WebElement currentUser;
		
		@FindBy(xpath="//div[@jsname='B34EJ']")
		List<WebElement> errorMessages;
		
		//Initialize Page Elements;
		public  EnterPasswordPage(WebDriver wd)
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
			elementsList.add(password);
			elementsList.add(nextButton);
			
			return elementsList;
		}

		public InboxPage populateAndSubmitEnterPasswordPage() throws InterruptedException, IOException, PasswordNotEnteredException, PasswordNotMatchedException, TimeoutException
		{
			waitForPageLoad();
			populate();
			return submit();
		}
		
		public void populate() throws InterruptedException, IOException
		{
			enterPassword();
		}
		
		public InboxPage submit() throws InterruptedException, PasswordNotEnteredException, PasswordNotMatchedException, TimeoutException
		{
			return clickNext();
		}
		
		public EnterPasswordPage enterPassword() throws InterruptedException, IOException
		{
			String passwordValue="";
			try
			{
				//passwordValue= properties.getProperty("passwordValue");
				passwordValue=DataProvider.getValue("passwordValue");
			}
			catch(NullPointerException e)
			{
				//System.out.println(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			try
			{
				password.sendKeys(passwordValue);
			}
			catch(ElementNotFoundException e)
			{
				//System.out.println(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
				logger.info(e.getStackTrace());
			}
			return this;
		}
		public InboxPage clickNext() throws InterruptedException, PasswordNotEnteredException, PasswordNotMatchedException, TimeoutException
		{
			nextButton.click();
			checkForErrors();
			
			return new InboxPage(wd);
		}
		
		//Method to check for error messages display
		private void checkForErrors() throws PasswordNotEnteredException, PasswordNotMatchedException, InterruptedException, TimeoutException 
		{
			//wd.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
			
			Thread.sleep(1000);
			List<WebElement> list=errorMessages;			
				if(!errorMessages.isEmpty())
				{
					for(WebElement errorMessage: list)
					{
						if(errorMessage.getText().contains(ErrorMessages.PASSWORD_NOT_ENTERED.getErrorMessage()))
							throw new PasswordNotEnteredException(ErrorMessages.PASSWORD_NOT_ENTERED.getErrorMessage());
						
						else if(errorMessage.getText().contains(ErrorMessages.PASSWORD_NOT_MATCHED.getErrorMessage()))
							throw new PasswordNotMatchedException(ErrorMessages.PASSWORD_NOT_MATCHED.getErrorMessage());
					}
				}
		}

		public boolean onHomePage()
		{
			return currentUser.isDisplayed();
		}

		@Override
		public String getDataPath() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
}
