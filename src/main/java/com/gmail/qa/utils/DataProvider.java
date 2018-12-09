package com.gmail.qa.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gmail.qa.base.Base;

public class DataProvider extends Base 
{
	
	//Method to read data from excel sheet and save them in Map.
    public static HashMap<String, String> getTestData(String url) throws IOException
	{
    	FileInputStream dataFile= new FileInputStream	(url);
		Workbook workbook= new XSSFWorkbook(dataFile);
		Sheet sheet= workbook.getSheetAt(0);
		HashMap<String, String> hashMap= new HashMap<>();
		int cell=0;
		while( cell<sheet.getRow(0).getLastCellNum())
		{
			hashMap.put(sheet.getRow(0).getCell(cell).toString(), sheet.getRow(1).getCell(cell).toString());
			cell=cell+1;
		}
		//workbook.close();
		return hashMap;
	}
	
    //Method to get value from Map for a specified key.
	public  static String getValue(String key) throws IOException
	{	
		String value="";
		value= map.get(key).toString();
		return value;
	}
    
	@Override
	public String getDataPath() throws IOException 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
