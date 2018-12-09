package com.gmail.qa.utils;

public enum ErrorMessages 
{
		EMAIL_NOT_ENTERED("Enter an email or phone number"),
		EMAIL_NOT_FOUND("Couldn't find your Google Account"),
		PASSWORD_NOT_ENTERED("Enter a password"),
		PASSWORD_NOT_MATCHED("Wrong password. Try again or click Forgot password to reset it.");
	
		private String errorMessage="";
		
		public String getErrorMessage()
		{
			return this.errorMessage;
		}
		
		private ErrorMessages(String errorMessage)
		{
			this.errorMessage=errorMessage;
		}
	};
