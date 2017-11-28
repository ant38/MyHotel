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

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.service.HotelService;

@Stateless
@ApplicationPath("/rest")
@Path("hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelRest extends Application {
	
	@Inject
	HotelService hotelService;
	
	@GET
	public List<HotelEntity> getHotels() {
		return hotelService.findAllHotelEntities();
	}
	
	@GET
	@Path("{id}")
	public HotelEntity getHotel(@PathParam("id") Long id) {
		return hotelService.find(id);
	}
}
