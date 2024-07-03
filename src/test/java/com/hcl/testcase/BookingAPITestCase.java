package com.hcl.testcase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.hcl.endpoints.BookingAPIEndpoints;
import com.hcl.payload.Booking;

import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;

public class BookingAPITestCase {
	BookingAPIEndpoints bookingEndpoint;
	Booking bookingDetails;
	int bookingId = 0;
	Faker faker;
	public static Logger logger = LogManager.getLogger("TestResults_Log");
	ResponseSpecification responseSpecification = null;
	ResponseSpecBuilder responsespecBuilder = null;

	@BeforeClass
	public void initialize() {
		bookingEndpoint = new BookingAPIEndpoints();
		faker = new Faker();
		buildBookingObj();
		responsespecBuilder = new ResponseSpecBuilder();
		responsespecBuilder.expectStatusCode(200);
		responsespecBuilder.expectResponseTime(Matchers.lessThan(3000L));
		responseSpecification = responsespecBuilder.build();
	}

	@Test(priority = 1, description = "Test Post method which create a booking")	
	public void testCreateBooking() throws JsonProcessingException {
		System.out.println(bookingDetails);
		if(bookingId == -1) {
			logger.info(bookingId + " Error while building booking object");
			throw new SkipException("Skipping CreateBooking Test as Booking Object is not build");	
		}
	
		Response response = bookingEndpoint.createBooking(bookingDetails);
		ValidatableResponse validateResponse = response.then();
		logger.info("Create New Booking  " + bookingId+"  "+response.asPrettyString());
		bookingId = validateResponse.assertThat().statusCode(200).extract().path("bookingid");
		logger.info(validateResponse.log().all());
	}
	@Test(priority = 2, description = "Test Get method for a given booking Id")
	public void testGetBookingId() {
		if(bookingId == 0) {
			logger.info(bookingId + " Skipping logger.info(bookingId + \" Skipping partialUpdateBooking Test as Id is invalid\"); Test as Id is invalid");
			throw new SkipException("Skipping GetBookingId Test as Id is invalid");
		}
			
		Response response = bookingEndpoint.getBookingDetails(bookingId);
		logger.info("GetBookingId "+bookingId+" booking id returns "+response.statusLine());
		logger.info(response.asPrettyString());
		bookingDetails = response.then().statusCode(200).extract().as(Booking.class);
		logger.info(bookingId + " Exist with below booking details");
		logger.info(response.asPrettyString());
	}
	 @Test(priority=2,description="Test Get method which will return all bookignids")
	public void getAllBookingIds() {
		Response response = bookingEndpoint.getAllBookingIds();
		response.then().spec(responseSpecification);
		int bookingCount = response.jsonPath().getInt("data.size()");
		logger.info("Found " + bookingCount + " Bookings");
	}
	@Test(priority = 5, description = "Test delete booking API")
	public void deleteBooking() {
		if(bookingId == 0) {
			logger.info(bookingId + " Skipping partialUpdateBooking Test as Id is invalid");
			throw new SkipException("Skipping GetBookingId Test as Id is invalid");
		}
					
		Response response = bookingEndpoint.deleteBooking(bookingId);
		response.then().log().all(); 
		logger.info("delete for "+bookingId+" status:"+response.statusCode());
		logger.info(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 201);
	}
	@Test(priority = 4, description = "Test update booking")
	// @Parameters({"bookingIdVal"})
	public void updateBooking() {
		if(bookingId == 0) {
			logger.info(bookingId + " Skipping partialUpdateBooking Test as Id is invalid");
			throw new SkipException("Skipping updateBooking Test as Id is invalid");
		}
		Response response = bookingEndpoint.updateBooking(bookingId, bookingDetails);
		response.then().log().all(); 
		logger.info("updateBooking for "+bookingId+" status:"+response.statusCode());
		logger.info(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test(priority = 3, description = "Test partial update booking")
	// @Parameters({"bookingIdVal"})
//	public void partialUpdateBooking(@Optional("1000") int bookingId) {
	public void partialUpdateBooking() {
		if(bookingId == 0) {
			logger.info(bookingId + " Skipping partialUpdateBooking Test as Id is invalid");
			throw new SkipException("Skipping partialUpdateBooking Test as Id is invalid");
			
		}
		Booking newBooking = new Booking();
		newBooking.setFirstname("ModifiedName43434");
		newBooking.setLastname("ModifiedName");
		Response response = bookingEndpoint.partialUpdateBooking(bookingId, newBooking);
		response.then().log().all(); 
		logger.info("partialUpdateBooking for "+bookingId+" status:"+response.statusCode());
		logger.info(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
	}

	public void buildBookingObj() {		
		try {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
		  bookingDetails = new Booking();
		  bookingDetails.setAdditionalneeds("Breakfast");
		  bookingDetails.setDepositpaid(false);
		  bookingDetails.setFirstname(faker.name().firstName()); 
		  bookingDetails.setBookingdates(formatter.parse("2024/10/10"),formatter.parse("2024/10/20"));
		  bookingDetails.setLastname(faker.name().lastName()); 
		  bookingDetails.setTotalprice(10);	
		/*
		 * bookingDetails.setAdditionalneeds("Breakfast");
		 * bookingDetails.setDepositpaid(false);
		 * bookingDetails.setFirstname(faker.name().firstName());
		 * bookingDetails.setBookingdates(faker.date().between(new
		 * Date("2024/10/10"),new Date("2024/10/20")), faker.date().between(new
		 * Date("2024/10/21"),new Date("2024/11/20")));
		 */

		// bookingDetails.setLastname(faker.name().lastName());
		int price = (int) (Math.random() * 100);
		bookingDetails.setTotalprice(price);
		}catch(Exception  e) {
			System.out.println("Exception while building booking obj"+e.getMessage());
			bookingId = -1;
		}

	}
}
