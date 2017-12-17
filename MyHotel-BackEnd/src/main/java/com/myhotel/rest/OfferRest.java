package com.myhotel.rest;

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

import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.service.OfferService;

@Stateless
@ApplicationPath("/rest")
@Path("offers")
@Produces(MediaType.APPLICATION_JSON)
public class OfferRest extends Application {

	@Inject
	private OfferService offerService;
	
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
			@QueryParam("dateOut") Date dateOut,
			@DefaultValue("0") @QueryParam("days") Long days) {
		
		List<OfferEntity> offers = offerService.getOffers(city, adults, children, dateIn, dateOut, days);
		return OfferService.headers(Response.ok(offers)).build();
	}
}
