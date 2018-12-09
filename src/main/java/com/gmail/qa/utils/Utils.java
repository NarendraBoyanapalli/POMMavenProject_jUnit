package com.gmail.qa.utils;

import java.io.IOException;

import com.gmail.qa.base.Base;

public class Utils extends Base
{
	public static long PAGE_LOAD_TIMEOUT=50;
	public static long IMPLICIT_WAIT=1000;
	
	public void switchFrame()
	{
		wd.switchTo().frame("");
	}

	@Override
	public String getDataPath() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
