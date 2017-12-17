package com.myhotel.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.service.BookingService;
import com.myhotel.beans.service.OfferService;

@Stateless
@ApplicationPath("/rest")
@Path("bookings")
@Produces(MediaType.APPLICATION_JSON)
public class BookingRest extends Application {

	@Inject
	private BookingService bookingService;
	
	@Inject
	private OfferService offerService;
	
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
	
	@GET
	@Path("book")
	public Response book(@QueryParam("offerId") Long offerId, @QueryParam("clientId") Long clientId, @QueryParam("paid") Long paid) {
		OfferEntity offer = offerService.find(offerId);
		BookingEntity booking = bookingService.book(offer, clientId, paid);
		return BookingService.headers(Response.ok(booking)).build();
	}
	
	@GET
	@Path("{id}/sendMail")
	public Response sendMail(@PathParam("id") Long id) {
		bookingService.sendMail(id);
		return BookingService.headers(Response.ok()).build();
	}
}
