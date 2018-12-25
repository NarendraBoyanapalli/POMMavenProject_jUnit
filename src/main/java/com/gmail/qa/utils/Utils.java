package com.gmail.qa.utils;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.gmail.qa.base.Base;

public class Utils extends Base
{
	public static long PAGE_LOAD_TIMEOUT=50;
	public static long IMPLICIT_WAIT=1000;
	Logger logger;
	
	public void switchFrame()
	{
		wd.switchTo().frame("");
	}

	public void getURL() throws IOException
	{
		String urlString= properties.getProperty("href");
		
		verifyURL(urlString);
	}
	
	public void verifyURL(String urlString) throws IOException
	{
		logger= Logger.getLogger(this.getClass().getSimpleName());
		URL url= new URL(urlString);
		HttpsURLConnection connection= (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(2000);
		connection.connect();
		if(connection.getResponseCode()==200)
			logger.info(connection.getResponseMessage());
		else if(connection.getResponseCode()==404)
			logger.info(connection.getResponseMessage());
		else if(connection.getResponseCode()==401)
			logger.info(connection.getResponseMessage());
		else if(connection.getResponseCode()==500)
			logger.info(connection.getResponseMessage());
	}
	@Override
	public String getDataPath() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
