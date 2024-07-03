package com.hcl.testcase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.endpoints.BookingAPIEndpoints;
import com.hcl.payload.Booking;
import com.hcl.utilities.BookingDataProvider;

import io.restassured.response.Response;

public class DataDrivenBookingTestCase {

	BookingAPIEndpoints bookingEndpoint;
	public static Logger logger = LogManager.getLogger("TestResults_Log");
	@BeforeClass
	public void initialize() {
		bookingEndpoint =  new BookingAPIEndpoints();
	}
	@Test(dataProvider = "BookingData", dataProviderClass = BookingDataProvider.class)
	public void testBulkCreateBooking(Booking bookingDetails) throws JsonProcessingException
	{
 
		Response response = bookingEndpoint.createBooking(bookingDetails);
 
 
		//log response
		response.then().log().all(); 
		
		logger.info("Bulk Bookings :"+response.asPrettyString());
 
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
 
 
	}
	@Test(dataProvider = "BookingIds", dataProviderClass = BookingDataProvider.class)
	public void testgetBookingDetails(int bookingId) throws JsonProcessingException
	{
 
		Response response = bookingEndpoint.getBookingDetails(bookingId);
 
 
		//log response
		response.then().log().all(); 
		logger.info("Bulk get Bookings details :"+response.asPrettyString());
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
 
 
	}	

}
