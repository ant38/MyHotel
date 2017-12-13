package com.myhotel.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response getClients() {
		List<ClientEntity> clients = clientService.findAllClientEntities();
		return ClientService.headers(Response.ok(clients)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getClient(@PathParam("id") Long id) {
		ClientEntity client = clientService.find(id);
		return ClientService.headers(Response.ok(client)).build();
	}
	
	@POST
	@Path("isClient")
	public Response isClient(@QueryParam("username") String username, @QueryParam("password") String password) {
		ClientEntity client = clientService.isClient(username, password);
		return ClientService.headers(Response.ok(client)).build();
	}

	@POST
	@Path("newClient")
	public Response newClient(
			@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("prenom") String prenom,
			@QueryParam("nom") String nom,
			@QueryParam("dateNaissance") Date dateNaissance,
			@QueryParam("email") String email) {
		Long result = clientService.newClient(username, password,prenom, nom, dateNaissance, email);
		return ClientService.headers(Response.ok(result==1)).build();
	}
}
