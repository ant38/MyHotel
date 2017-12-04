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
	public List<RoomEntity> getRooms() {
		return roomService.findAllRoomEntities();
	}
	
	@GET
	@Path("{id}")
	public RoomEntity getRoom(@PathParam("id") Long id) {
		return roomService.find(id);
	}
}
