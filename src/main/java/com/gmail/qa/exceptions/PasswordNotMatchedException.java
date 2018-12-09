package com.gmail.qa.exceptions;

public class PasswordNotMatchedException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordNotMatchedException(String message)
	{
		super(message);
	}
}
