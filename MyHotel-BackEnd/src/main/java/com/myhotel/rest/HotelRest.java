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

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.HotelImage;
import com.myhotel.beans.service.HotelService;

@Stateless
@ApplicationPath("/rest")
@Path("hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelRest extends Application {
	
	@Inject
	HotelService hotelService;
	
	@GET
	public Response getHotels() {
		List<HotelEntity> hotels = hotelService.findAllHotelEntities();
		return HotelService.headers(Response.ok(hotels)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getHotel(@PathParam("id") Long id) {
		HotelEntity hotel = hotelService.find(id);
		return HotelService.headers(Response.ok(hotel)).build();
	}
	
	@GET
	@Path("{id}/getImage")
	public Response getImage(@PathParam("id") Long id) {
		HotelEntity hotel = hotelService.find(id);
		HotelImage image = hotel.getImage();
		return HotelService.headers(Response.ok(image)).build();
	}
}
