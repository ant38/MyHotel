package com.myhotel.beans.service;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.ClientEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class ClientService extends BaseService<ClientEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public ClientService(){
        super(ClientEntity.class);
    }
    
    @Transactional
    public List<ClientEntity> findAllClientEntities() {
        
        return entityManager.createQuery("SELECT o FROM Client o ", ClientEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Client o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(ClientEntity client) {

        /* This is called before a Client is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        this.cutAllClientCommentsAssignments(client);
        
        this.cutAllClientSavesAssignments(client);
        
    }

    // Remove all assignments from all comment a client. Called before delete a client.
    @Transactional
    private void cutAllClientCommentsAssignments(ClientEntity client) {
        entityManager
                .createQuery("UPDATE UserComment c SET c.client = NULL WHERE c.client = :p")
                .setParameter("p", client).executeUpdate();
    }
    
    // Remove all assignments from all save a client. Called before delete a client.
    @Transactional
    private void cutAllClientSavesAssignments(ClientEntity client) {
        entityManager
                .createQuery("UPDATE Save c SET c.client = NULL WHERE c.client = :p")
                .setParameter("p", client).executeUpdate();
    }
    
    @Transactional
    public List<ClientEntity> findAvailableClients(BookingEntity booking) {
        return entityManager.createQuery("SELECT o FROM Client o where o.id not in (select o.id from Client o join o.bookings p where p = :p)", ClientEntity.class).setParameter("p", booking).getResultList();
    }

    @Transactional
    public List<ClientEntity> findClientsByBooking(BookingEntity booking) {
        return entityManager.createQuery("SELECT o FROM Client o where o.id in (select o.id from Client o join o.bookings p where p = :p)", ClientEntity.class).setParameter("p", booking).getResultList();
    }

    @Transactional
    public ClientEntity fetchBookings(ClientEntity client) {
        client = find(client.getId());
        client.getBookings().size();
        return client;
    }
    
    @Transactional
    public long isClient(String username, String password) {
    	return entityManager
    			.createQuery("SELECT COUNT(o) FROM Client o WHERE o.username = :username AND o.password = :password", Long.class)
    			.setParameter("username", username)
    			.setParameter("password", password)
    			.getSingleResult();
    }

    @Transactional
    public long newClient(String username, String password,String prenom, String nom) {
        return entityManager.createQuery("INSERT INTO Client o VALUES (username,password,prenom,nom)", ClientEntity.class).setParameter("username", username)
    			.setParameter("password", password)
                        .setParameter("prenom", prenom)
                        .setParameter("nom", nom).executeUpdate();
    }
    
}
