package com.myhotel.beans.service;

import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.domain.SaveEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class OfferService extends BaseService<OfferEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public OfferService(){
        super(OfferEntity.class);
    }
    
    @Transactional
    public List<OfferEntity> findAllOfferEntities() {
        
        return entityManager.createQuery("SELECT o FROM Offer o ", OfferEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Offer o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(OfferEntity offer) {

        /* This is called before a Offer is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    @Transactional
    public List<OfferEntity> findAvailableOffers(RoomEntity room) {
        return entityManager.createQuery("SELECT o FROM Offer o where o.id not in (select o.id from Offer o join o.rooms p where p = :p)", OfferEntity.class).setParameter("p", room).getResultList();
    }

    @Transactional
    public List<OfferEntity> findOffersByRoom(RoomEntity room) {
        return entityManager.createQuery("SELECT o FROM Offer o where o.id in (select o.id from Offer o join o.rooms p where p = :p)", OfferEntity.class).setParameter("p", room).getResultList();
    }

    @Transactional
    public List<OfferEntity> findAvailableOffers(SaveEntity save) {
        return entityManager.createQuery("SELECT o FROM Offer o where o.id not in (select o.id from Offer o join o.saves p where p = :p)", OfferEntity.class).setParameter("p", save).getResultList();
    }

    @Transactional
    public List<OfferEntity> findOffersBySave(SaveEntity save) {
        return entityManager.createQuery("SELECT o FROM Offer o where o.id in (select o.id from Offer o join o.saves p where p = :p)", OfferEntity.class).setParameter("p", save).getResultList();
    }

    @Transactional
    public OfferEntity fetchRooms(OfferEntity offer) {
        offer = find(offer.getId());
        offer.getRooms().size();
        return offer;
    }
    
    @Transactional
    public void insert(OfferEntity offer) {
    	entityManager.persist(offer);
    }
    
}
