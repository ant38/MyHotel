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

import com.myhotel.beans.domain.SaveEntity;
import com.myhotel.beans.service.SaveService;

@Stateless
@ApplicationPath("/rest")
@Path("saves")
@Produces(MediaType.APPLICATION_JSON)
public class SaveRest extends Application {

	@Inject
	private SaveService saveService;
	
	@GET
	public List<SaveEntity> getSaves() {
		return saveService.findAllSaveEntities();
	}
	
	@GET
	@Path("{id}")
	public SaveEntity getSave(@PathParam("id") Long id) {
		return saveService.find(id);
	}
}
