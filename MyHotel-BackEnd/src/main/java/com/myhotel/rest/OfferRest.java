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

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.OfferService;

@Stateless
@ApplicationPath("/rest")
@Path("offers")
@Produces(MediaType.APPLICATION_JSON)
public class OfferRest extends Application {

	@Inject
	private OfferService offerService;
	
	@Inject
	private HotelService hotelService;
	
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
	public Response search(@QueryParam("city") String city) {
		List<HotelEntity> hotels = hotelService.findHotelsByCity(city);
		return OfferService.headers(Response.ok(hotels)).build();
	}
}