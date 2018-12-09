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
import com.gmail.qa.pages.InboxPage;

public class GmailSignInComposeMailSignOutTest extends Base 
{
	public EnterEmailPage enterEmailPage;
	public InboxPage inboxPage;
	
	public GmailSignInComposeMailSignOutTest()
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
	@Retry(0)
	public void testGmailSignInComposeMailSignOut() throws InterruptedException, IOException, EmailNotEnteredException, EmailNotFoundException, PasswordNotEnteredException, PasswordNotMatchedException, TimeoutException
	{
		inboxPage= enterEmailPage.populateAndSubmitEnterEmailPage()
		.populateAndSubmitEnterPasswordPage()
		.composeMail();
		boolean indicator= inboxPage.isMailDisplayed();
		inboxPage.clickProfileIcon().clickSignOut();
		Assert.assertTrue("Mail isn't sent",indicator);
	}
	
	@After
	public void end()
	{
		tearDown();
	}

	@Override
	public String getDataPath() throws IOException 
	{
		String dataPath="C:\\Let's Code!\\POMMavenProject_jUnit\\src\\main\\java\\com\\gmail\\qa\\testData\\"+this.getClass().getSimpleName()+".xlsx";
		return dataPath;
	}

}
