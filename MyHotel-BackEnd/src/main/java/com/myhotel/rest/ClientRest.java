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

import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.service.ClientService;

@Stateless
@ApplicationPath("/rest")
@Path("clients")
@Produces(MediaType.APPLICATION_JSON)
public class ClientRest extends Application {

	@Inject
	private ClientService clientService;
	
	@GET
	public List<ClientEntity> getClients() {
		return clientService.findAllClientEntities();
	}
	
	@GET
	@Path("{id}")
	public ClientEntity getClient(@PathParam("id") Long id) {
		return clientService.find(id);
	}
}
