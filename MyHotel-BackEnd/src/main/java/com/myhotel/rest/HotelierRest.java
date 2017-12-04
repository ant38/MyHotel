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

import com.myhotel.beans.domain.HotelierEntity;
import com.myhotel.beans.service.HotelierService;

@Stateless
@ApplicationPath("/rest")
@Path("hoteliers")
@Produces(MediaType.APPLICATION_JSON)
public class HotelierRest extends Application {
	
	@Inject
	private HotelierService hotelierService;
	
	@GET
	public Response getHoteliers() {
		List<HotelierEntity> hoteliers = hotelierService.findAllHotelierEntities();
		return HotelierService.headers(Response.ok(hoteliers)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getHotelier(@PathParam("id") Long id) {
		HotelierEntity hotelier = hotelierService.find(id);
		return HotelierService.headers(Response.ok(hotelier)).build();
	}
}
