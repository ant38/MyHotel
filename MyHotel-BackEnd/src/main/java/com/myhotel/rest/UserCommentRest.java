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

import com.myhotel.beans.domain.UserCommentEntity;
import com.myhotel.beans.domain.UserCommentImage;
import com.myhotel.beans.service.UserCommentService;

@Stateless
@ApplicationPath("/rest")
@Path("usercomments")
@Produces(MediaType.APPLICATION_JSON)
public class UserCommentRest extends Application {

	@Inject
	private UserCommentService userCommentService;
	
	@GET
	public Response getUserComments() {
		List<UserCommentEntity> userComments = userCommentService.findAllUserCommentEntities();
		return UserCommentService.headers(Response.ok(userComments)).build();
	}
	
	@GET
	@Path("{id}")
	public Response getUserComment(@PathParam("id") Long id) {
		UserCommentEntity userComment = userCommentService.find(id);
		return UserCommentService.headers(Response.ok(userComment)).build();
	}
	
	@GET
	@Path("{id}/getImage")
	public Response getImage(@PathParam("id") Long id) {
		UserCommentEntity userComment = userCommentService.find(id);
		UserCommentImage image = userComment.getImage();
		return UserCommentService.headers(Response.ok(image)).build();
	}
}
