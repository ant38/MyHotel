package com.myhotel.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myhotel.beans.domain.HotelEntity;

@Stateless
@ApplicationPath("/rest")
@Path("hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelRest extends Application {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GET
	public List<HotelEntity> getHotels() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<HotelEntity> cq = cb.createQuery(HotelEntity.class);
        Root<HotelEntity> rootEntry = cq.from(HotelEntity.class);
        CriteriaQuery<HotelEntity> all = cq.select(rootEntry);
        TypedQuery<HotelEntity> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
	}
	
	@GET
	@Path("{id}")
	public Response getHotel(@PathParam("id") Long id) {
		HotelEntity hotel = null;
		try {

			hotel = entityManager.find(HotelEntity.class, id);
			if (hotel == null) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("hotel " + id + " unfound").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(getExceptionMessage(e)).build();
		}
		return Response.ok(hotel).build();
	}
	
	private String getExceptionMessage(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return e.getLocalizedMessage() + "/n" + errors.toString();
	}
}
