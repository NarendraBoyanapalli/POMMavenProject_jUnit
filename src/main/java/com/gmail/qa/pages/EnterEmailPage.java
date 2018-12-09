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
import com.gmail.qa.exceptions.EmailNotEnteredException;
import com.gmail.qa.exceptions.EmailNotFoundException;
import com.gmail.qa.utils.DataProvider;
import com.gmail.qa.utils.ErrorMessages;
import com.gmail.qa.utils.Utils;

public class EnterEmailPage extends Base 
{
	Logger logger= Logger.getLogger(this.getClass().getSimpleName());
	Utils utils= new Utils();
	//DataProvider dataProvider= new DataProvider();
	
	//Page Elements
	@FindBy(xpath="//input[@type='email']")
	WebElement email;
	
	@FindBy(xpath="//span[@class='RveJvd snByac']")
	WebElement nextButton;
	
	@FindBy(xpath="//div[@jsname='B34EJ']")
	List<WebElement> errorMessages;
	
	//Initialize Page Elements;
	public  EnterEmailPage(WebDriver wd)
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
		elementsList.add(email);
		elementsList.add(nextButton);
		
		return elementsList;
	}
	
	public EnterPasswordPage populateAndSubmitEnterEmailPage() throws IOException, EmailNotEnteredException, EmailNotFoundException, TimeoutException
	{
		waitForPageLoad();
		populate();
		return submit();
	}
	
	public void populate() throws IOException
	{
			enterEmail();
	}
	
	public EnterPasswordPage submit() throws EmailNotEnteredException, EmailNotFoundException, TimeoutException
	{
		return clickNext();
	}
	
	public EnterEmailPage enterEmail() throws IOException
	{
		String emailValue="";
		try
		{
			//emailValue= properties.getProperty("emailValue");
			emailValue= DataProvider.getValue("emailValue");
		}
		catch(NullPointerException e)
		{
			//System.out.println(e.getMessage()+" on "+this.getClass().getSimpleName());
			logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
			logger.info(e.getStackTrace());
		}
		try
		{
			email.sendKeys(emailValue);
		}
		catch(ElementNotFoundException e)
		{
			//System.out.println(e.getMessage()+" on "+this.getClass().getSimpleName());
			logger.warn(e.getMessage()+" on "+this.getClass().getSimpleName());
			logger.info(e.getStackTrace());
		}
		return this;
	}
	public EnterPasswordPage clickNext() throws EmailNotEnteredException, EmailNotFoundException, TimeoutException
	{
		nextButton.click();
		checkForErrors();
		return new EnterPasswordPage(wd);
	}
	
	//Method to check for error messages display
	private void checkForErrors() throws EmailNotEnteredException, EmailNotFoundException, TimeoutException
	{
		List<WebElement> list=errorMessages;
		
			if(!errorMessages.isEmpty())
			{
				for(WebElement errorMessage: list)
				{
						if(errorMessage.getText().contains(ErrorMessages.EMAIL_NOT_ENTERED.getErrorMessage()))
							throw new EmailNotEnteredException(ErrorMessages.EMAIL_NOT_ENTERED.getErrorMessage());
						
						else if(errorMessage.getText().contains(ErrorMessages.EMAIL_NOT_FOUND.getErrorMessage()))
							throw new EmailNotFoundException(ErrorMessages.EMAIL_NOT_FOUND.getErrorMessage());
				}
			}
	}

	public boolean pageTitle()
	{
		return wd.getTitle().equals("Gmail");
	}

	@Override
	public String getDataPath() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}