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

import com.myhotel.beans.domain.UserCommentEntity;
import com.myhotel.beans.service.UserCommentService;

@Stateless
@ApplicationPath("/rest")
@Path("usercomments")
@Produces(MediaType.APPLICATION_JSON)
public class UserCommentRest extends Application {

	@Inject
	private UserCommentService userCommentService;
	
	@GET
	public List<UserCommentEntity> getUserComments() {
		return userCommentService.findAllUserCommentEntities();
	}
	
	@GET
	@Path("{id}")
	public UserCommentEntity getUserComment(@PathParam("id") Long id) {
		return userCommentService.find(id);
	}
}
