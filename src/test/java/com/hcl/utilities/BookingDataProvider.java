package com.hcl.utilities;

import java.text.ParseException;

import org.testng.annotations.DataProvider;
import com.hcl.payload.Booking;
public class BookingDataProvider {

	@DataProvider(name="BookingData")
	public Booking [] bookingData() throws ParseException
	{
		String fName = System.getProperty("user.dir")+"//TestData//TestData.xlsx";
		Booking bookingDetails[] = ReadExcelFile.getBookingTestData(fName,"data");
		return bookingDetails;
	}	
	
	@DataProvider(name="BookingIds")
	public Integer [] bookingIds() throws ParseException
	{
		String fName = System.getProperty("user.dir")+"//TestData//TestData.xlsx";
		Integer bookingIDs[] = ReadExcelFile.getBookingIdsTestData(fName,"Sheet1");
		return bookingIDs;
	}	
}