package com.hcl.utilities;


import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hcl.payload.Booking;
import com.hcl.payload.Bookingdates;
 
public class ReadExcelFile {
 
 
	public static FileInputStream inputStream;
	public static XSSFWorkbook workBook;
	public static XSSFSheet excelSheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	
	public static String[][] getBookingDataFromExcel(String fileName, String sheetName) {
		String userData[][]=null;
		try
		{
 
			inputStream = new FileInputStream(fileName);
			workBook = new XSSFWorkbook(inputStream);
			excelSheet =  workBook.getSheet(sheetName);
			int ttlRows = excelSheet.getLastRowNum() + 1;
			int ttlCells = excelSheet.getRow(0).getLastCellNum();
			System.out.println("No of row in "+sheetName+" :"+ttlRows);
			System.out.println("No of columns in "+sheetName+" :"+ttlCells);
			userData = new String[ttlRows-1][ttlCells];			
			for(int rowNo = 1; rowNo<ttlRows; rowNo++)
			{
				for(int colNo=0; colNo<ttlCells; colNo++)
				{
					userData[rowNo-1][colNo] = excelSheet.getRow(rowNo).getCell(colNo).toString();
					System.out.println("Cell value "+rowNo+"  "+colNo+userData[rowNo-1][colNo]);
				}
			}		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return userData;
	}
 
	public static Booking[] getBookingTestData(String fName,String sheet) throws ParseException {
		String userData[][] = getBookingDataFromExcel(fName,sheet);
		Booking[] booking_details = new Booking[userData.length];
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		for(int i=0;i<booking_details.length;i++) {
			booking_details[i] = new Booking();
			booking_details[i].setFirstname(userData[i][0]);
			booking_details[i].setLastname(userData[i][1]);
			booking_details[i].setTotalprice(Double.parseDouble(userData[i][2]));
			booking_details[i].setDepositpaid(userData[i][3].equalsIgnoreCase("TRUE")?true:false);
			booking_details[i].setBookingdates(formatter.parse(userData[i][4]),formatter.parse(userData[i][5]));
			booking_details[i].setAdditionalneeds(userData[i][6]);			
			System.out.println(booking_details[i]);
		}
		return booking_details;
	}
	
	public static Integer[] getBookingIdsTestData(String fName,String sheet) throws ParseException {
		String userData[][] = getBookingDataFromExcel(fName,sheet);
		Integer[] bookingIds = new Integer[userData.length];String st;
		for(int i=0;i<bookingIds.length;i++) {
			st = userData[i][0];
			st = st.substring(0,st.indexOf(".")==-1?st.length():st.indexOf("."));
			bookingIds[i]=Integer.parseInt(st);
			System.out.println("Booking Id from excel:"+bookingIds[i]);
		}
		return bookingIds;
	}
	public static void main(String[] args) throws ParseException {
		String fName = System.getProperty("user.dir")+"\\TestData\\TestData.xlsx";
		System.out.println(fName);
		String userData[][] = getBookingDataFromExcel(fName,"data");
		Booking[] booking_details = new Booking[userData.length];
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		for(int i=0;i<booking_details.length;i++) {
			booking_details[i] = new Booking();
			booking_details[i].setFirstname(userData[i][0]);
			booking_details[i].setLastname(userData[i][1]);
			booking_details[i].setTotalprice(Double.parseDouble(userData[i][2]));
			booking_details[i].setDepositpaid(userData[i][3].equalsIgnoreCase("TRUE")?true:false);
			booking_details[i].setBookingdates(formatter.parse(userData[i][4]),formatter.parse(userData[i][5]));
			booking_details[i].setAdditionalneeds(userData[i][6]);			
			System.out.println(booking_details[i]);
		}
	}
 
}