package com.gmail.qa.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({GmailSignInAndSignOutTest.class, GmailSignInComposeMailSignOutTest.class, GmailSignInComposeMailDeleteSentItemSignOutTest.class})
public class RunAllTest 
{
}
