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
	public List<HotelierEntity> getHoteliers() {
		return hotelierService.findAllHotelierEntities();
	}
	
	@GET
	@Path("{id}")
	public HotelierEntity getHotelier(@PathParam("id") Long id) {
		return hotelierService.find(id);
	}
}
