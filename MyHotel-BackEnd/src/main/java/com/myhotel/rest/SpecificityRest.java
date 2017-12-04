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

import com.myhotel.beans.domain.SpecificityEntity;
import com.myhotel.beans.service.SpecificityService;

@Stateless
@ApplicationPath("/rest")
@Path("specificities")
@Produces(MediaType.APPLICATION_JSON)
public class SpecificityRest extends Application {

	@Inject
	private SpecificityService specityService;
	
	@GET
	public List<SpecificityEntity> getSpecificities() {
		return specityService.findAllSpecificityEntities();
	}
	
	@GET
	@Path("{id}")
	public SpecificityEntity getSpecificity(@PathParam("id") Long id) {
		return specityService.find(id);
	}
	
}
