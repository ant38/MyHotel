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
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.domain.SpecificityEntity;
import com.myhotel.beans.domain.UserCommentEntity;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.RoomService;
import com.myhotel.beans.service.SpecificityService;
import com.myhotel.beans.service.UserCommentService;

@Stateless
@ApplicationPath("/rest")
@Path("hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelRest extends Application {
	
	@Inject
	HotelService hotelService;
	
	@Inject
	SpecificityService specificityService;
	@Inject
	UserCommentService userCommentService;
	@Inject
	RoomService roomService;
	
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
	
	@GET
	@Path("{id}/getSpecificities")
	public Response getSpecificities(@PathParam("id") Long id) {
		HotelEntity hotel = hotelService.find(id);
		List<SpecificityEntity> specificities = specificityService.findSpecificitysByHotel(hotel);
		return HotelService.headers(Response.ok(specificities)).build();
	}
	
	@GET
	@Path("{id}/getUserComments")
	public Response getUserComments(@PathParam("id") Long id) {
		HotelEntity hotel = hotelService.find(id);
		List<UserCommentEntity> userComments = userCommentService.findCommentsByHotel(hotel);
		return HotelService.headers(Response.ok(userComments)).build();
	}
	
	@GET
	@Path("{id}/getRooms")
	public Response getRooms(@PathParam("id") Long id) {
		HotelEntity hotel = hotelService.find(id);
		List<RoomEntity> rooms = roomService.findRoomsByHotel(hotel);
		return HotelService.headers(Response.ok(rooms)).build();
	}
}
