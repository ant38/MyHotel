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

import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.service.RoomService;

@Stateless
@ApplicationPath("/rest")
@Path("rooms")
@Produces(MediaType.APPLICATION_JSON)
public class RoomRest extends Application {
	
	@Inject
	private RoomService roomService;
	
	@GET
	public Response getRooms() {
		List<RoomEntity> rooms = roomService.findAllRoomEntities();
		return RoomService.headers(Response.ok(rooms)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getRoom(@PathParam("id") Long id) {
		RoomEntity room = roomService.find(id);
		return RoomService.headers(Response.ok(room)).build();
	}
}
