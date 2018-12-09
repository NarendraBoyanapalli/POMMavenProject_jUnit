package com.gmail.qa.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.gmail.qa.base.Base;
import com.gmail.qa.base.Retry;
import com.gmail.qa.base.RetryRule;
import com.gmail.qa.exceptions.EmailNotEnteredException;
import com.gmail.qa.exceptions.EmailNotFoundException;
import com.gmail.qa.exceptions.PasswordNotEnteredException;
import com.gmail.qa.exceptions.PasswordNotMatchedException;
import com.gmail.qa.pages.EnterEmailPage;
import com.gmail.qa.pages.EnterPasswordPage;

public class GmailSignInAndSignOutTest extends Base 
{
	public EnterEmailPage enterEmailPage;
	public EnterPasswordPage enterPasswordPage;
	
	public GmailSignInAndSignOutTest()
	{
		super();
	}
	
	@Before
	public void start() throws IOException
	{
		enterEmailPage= super.setup();
	}
	
	@Rule
	public RetryRule repeatRule = new RetryRule();
	
	@Test
	@Retry(1)
	public void testGmailSignInAndSignOut() throws InterruptedException, IOException, EmailNotEnteredException, EmailNotFoundException, PasswordNotEnteredException, PasswordNotMatchedException, TimeoutException
	{
		enterPasswordPage=enterEmailPage.populateAndSubmitEnterEmailPage()
		.populateAndSubmitEnterPasswordPage()
		.clickProfileIcon()
		.clickSignOut();
		
		Assert.assertTrue(enterPasswordPage.onHomePage());
	}
	
	@After
	public void end()
	{
		tearDown();
	}

	@Override
	public String  getDataPath() throws IOException
	{
		String dataPath="C:\\Let's Code!\\POMMavenProject_jUnit\\src\\main\\java\\com\\gmail\\qa\\testData\\"+this.getClass().getSimpleName()+".xlsx";
		return dataPath;
	}
}
