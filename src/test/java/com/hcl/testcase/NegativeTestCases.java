package com.hcl.testcase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.endpoints.BookingAPIEndpoints;
import com.hcl.payload.Booking;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class NegativeTestCases {
	public static Logger logger = LogManager.getLogger("TestResults_Log");
	BookingAPIEndpoints bookingEndpoint;
	@BeforeClass
	void initialize() {
		bookingEndpoint = new BookingAPIEndpoints();
	}
	@Test(priority = 2, description = "Test Get method for a given booking Id for Invalid id")
	@Parameters({"bookingIdVal"})
	public void testGetBookingIdForInvalidId(@Optional("-1") int bookingId) {
		Response response = bookingEndpoint.getBookingDetails(bookingId);
		Assert.assertEquals(response.statusCode(), 404);
		logger.info(" Server responded with "+response.statusCode()+" for bookind id "+bookingId);
		response.asPrettyString();
	}
	@Test( description = "create a booking with invalid data")
	public void testCreateBooking() throws JsonProcessingException {
		Booking bookingDetails = new Booking(); 
		Response response = bookingEndpoint.createBooking(bookingDetails);
		Assert.assertEquals(response.statusCode(),500,"Internal Server error as payload is incorrect");
		response.asPrettyString();
	}
	@Test(description = "Test update method for Invalid id")
	@Parameters({"bookingIdVal"})
	public void testUpdateBookingIdForInvalidId(@Optional("-1") int bookingId) {
		Booking bookingDetails = new Booking(); 
		Response response = bookingEndpoint.updateBooking(bookingId,bookingDetails);
		Assert.assertEquals(response.statusCode(), 400);
		logger.info(" Server responded with "+response.statusCode()+" for bookind id "+bookingId);
		response.asPrettyString();
	}	
	@Test(description = "Test delete method for Invalid id")
	@Parameters({"bookingIdVal"})
	public void testDeleteBookingIdForInvalidId(@Optional("-1") int bookingId) {
		Response response = bookingEndpoint.deleteBooking(bookingId);
		Assert.assertEquals(response.statusCode(), 405);
		logger.info(" Server responded with "+response.statusCode()+" for bookind id "+bookingId);
		response.asPrettyString();
	}	
}
