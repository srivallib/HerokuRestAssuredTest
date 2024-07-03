package com.hcl.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

 @JsonInclude(JsonInclude.Include.NON_DEFAULT)
 public class Bookingdates{
    @Override
	public String toString() {
		return "\nBookingdates [checkin=" + checkin + ", checkout=" + checkout + "]\n";
	}
	public Date getCheckin() {
		return checkin;
	}
	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}
	public Date getCheckout() {
		return checkout;
	}
	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}
	private Date checkin;
    private Date checkout;
}