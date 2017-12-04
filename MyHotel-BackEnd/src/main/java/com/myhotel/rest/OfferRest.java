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
	public List<OfferEntity> getOffers() {
		return offerService.findAllOfferEntities();
	}
	
	@GET
	@Path("{id}")
	public OfferEntity getOffer(@PathParam("id") Long id) {
		return offerService.find(id);
	}
}
