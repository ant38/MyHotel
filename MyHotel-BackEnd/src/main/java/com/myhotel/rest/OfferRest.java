package com.myhotel.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.service.BookingService;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.OfferService;
import com.myhotel.beans.service.RoomService;

@Stateless
@ApplicationPath("/rest")
@Path("offers")
@Produces(MediaType.APPLICATION_JSON)
public class OfferRest extends Application {

	@Inject
	private OfferService offerService;
	
	@Inject
	private HotelService hotelService;
	@Inject
	private RoomService roomService;
	@Inject
	private BookingService bookingService;
	
	@GET
	public Response getOffers() {
		List<OfferEntity> offers = offerService.findAllOfferEntities();
		return OfferService.headers(Response.ok(offers)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getOffer(@PathParam("id") Long id) {
		OfferEntity offer = offerService.find(id);
		return OfferService.headers(Response.ok(offer)).build();
	}
	
	@GET
	@Path("search")
	public Response search(
			@DefaultValue("") @QueryParam("city") String city,
			@QueryParam("adults") Long adults,
			@QueryParam("children") Long children,
			@QueryParam("dateIn") Date dateIn,
			@QueryParam("dateOut") Date dateOut) {
		List<HotelEntity> hotels = hotelService.findHotelsByCity(city);
		List<RoomEntity> rooms = roomService.findRooms(hotels, adults, children, dateIn, dateOut);
		
		List<OfferEntity> offers = new ArrayList<>();
		
		for(int i=0; i<rooms.size(); i++) {
			OfferEntity offer = new OfferEntity();
			List<RoomEntity> offerRooms = new ArrayList<>();
			offerRooms.add(rooms.get(i));
			offer.setRooms(offerRooms);
			offer.setDateStart(dateIn);
			offer.setDateEnd(dateOut);
			offer.setPrice(100);
			offerService.insert(offer);
			offers.add(offer);
		}
		
		return OfferService.headers(Response.ok(offers)).build();
	}
}
