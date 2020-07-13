package com.cp.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	 public FileInputStream fis = null;
	   public XSSFWorkbook workbook = null;
	   public XSSFSheet sheet = null;
	   public XSSFRow row = null;
	   public XSSFCell cell = null;
	   String xlFilePath;

	
	public static void main(String [] args) throws IOException
	{
		File src = new File("E:\\MobileAutomationPOC\\CustomerPortalAutomation\\TestData\\CPFramework.xlsx");
		
		FileInputStream fis  = new FileInputStream(src);
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		XSSFSheet Sheet = wb.getSheetAt(3);
		
		XSSFRow row = Sheet.getRow(1);
		
		int rowcount = Sheet.getLastRowNum();
		System.out.println("Total Number of Rows Present in the Sheet : " + rowcount);
		
		int colcount = Sheet.getRow(1).getLastCellNum();
		System.out.println("Total Number of Coloumn Present in the Sheet : " + colcount);
		
		for(int i=1; i <=rowcount;i++)
		{
			for(int j=1;j<=colcount; j++)
			{
				XSSFCell cell = Sheet.getRow(i).getCell(j);
				String celltext="";
				System.out.println(cell);
				
				//Get Celltype Values
				if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				{
					celltext=cell.getStringCellValue();
				}
				else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				{
					celltext=String.valueOf(Cell.CELL_TYPE_NUMERIC);
				}
				else if (cell.getCellType()==Cell.CELL_TYPE_BLANK)
				{
					celltext="";
				}
				
			}
		}
		
		
    
	}
	
	public ReadExcel(String xlFilePath) throws Exception
	   {
	       this.xlFilePath = xlFilePath;
	       fis = new FileInputStream(xlFilePath);
	       workbook = new XSSFWorkbook(fis);
	       fis.close();
	   }
	 
	   public int getRowCount(String sheetName)
	   {
	       sheet = workbook.getSheet(sheetName);
	       int rowCount = sheet.getLastRowNum()+1;
	       return rowCount;
	   }
}

