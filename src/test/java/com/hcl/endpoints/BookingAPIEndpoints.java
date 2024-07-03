package com.hcl.endpoints;

import java.util.ResourceBundle;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.hcl.payload.Booking;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookingAPIEndpoints {
	
	ResourceBundle routes;
	RequestSpecBuilder reqSpecBuilder;
	RequestSpecification reqSpec;
	public BookingAPIEndpoints(){
		routes = ResourceBundle.getBundle("endpoints");
		reqSpecBuilder=new RequestSpecBuilder();
		reqSpecBuilder
		.addHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
		.addHeader("Content-Type","application/json");
		reqSpec=reqSpecBuilder.build();
		
	}//JsonSchemaValidator.matchesJsonSchemaInClasspath("response.schema")
	public Response createBooking(Booking bookingDetails) {
		Response response = RestAssured.given().spec(reqSpec)				
				.body(bookingDetails)
				//.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("bookingPayLoad.schema"))
				.log().all()
		.post(routes.getString("createBooking_url"));
		return response;
	}
	public Response getBookingDetails(int bookingId) {
		Response response = RestAssured.given()
				.pathParam("bookingid", bookingId)
		.get(routes.getString("getId_url"));
		return response;
	}	
	public Response getAllBookingIds() {
		Response response = RestAssured.given()	
		.get(routes.getString("getAllIds_url"));
		return response;
	}	
	public Response deleteBooking(int bookingId) {
		Response response = RestAssured.given()	
				.pathParam("bookingid", bookingId).spec(reqSpec)
		.delete(routes.getString("deleteBooking_url"));
		return response;
	}	
	public Response updateBooking(int bookingId,Booking bookingDetails) {
		Response response = RestAssured.given()	
				.pathParam("bookingid", bookingId).spec(reqSpec)
				.body(bookingDetails)
		.put(routes.getString("updateBooking_url"));
		return response;
	}	
	public Response partialUpdateBooking(int bookingId,Booking bookingDetails) {
		Response response = RestAssured.given().spec(reqSpec)
				.pathParam("bookingid", bookingId)
				.body(bookingDetails).log().all()
				//.log().all();
		.patch(routes.getString("patchBooking_url"));
		return response;
	}	
}
