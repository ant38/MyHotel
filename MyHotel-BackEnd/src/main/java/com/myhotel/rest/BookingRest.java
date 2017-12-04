package com.myhotel.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.service.BookingService;

@Stateless
@ApplicationPath("/rest")
@Path("bookings")
@Produces(MediaType.APPLICATION_JSON)
public class BookingRest extends Application {

	@Inject
	private BookingService bookingService;
	
	@GET
	public Response getBookings() {
		List<BookingEntity> bookings = bookingService.findAllBookingEntities();
		return BookingService.headers(Response.ok(bookings)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getBooking(@PathParam("id") Long id) {
		BookingEntity booking = bookingService.find(id);
		return BookingService.headers(Response.ok(booking)).build();
	}
}
