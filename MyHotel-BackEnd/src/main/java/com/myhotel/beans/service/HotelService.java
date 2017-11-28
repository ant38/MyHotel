package com.myhotel.beans.service;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.HotelierEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceUnitUtil;
import javax.transaction.Transactional;

@Named
public class HotelService extends BaseService<HotelEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public HotelService(){
        super(HotelEntity.class);
    }
    
    @Inject
    private HotelAttachmentService attachmentService;
    
    @Transactional
    public List<HotelEntity> findAllHotelEntities() {
        
        return entityManager.createQuery("SELECT o FROM Hotel o LEFT JOIN FETCH o.image", HotelEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Hotel o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(HotelEntity hotel) {

        /* This is called before a Hotel is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        this.attachmentService.deleteAttachmentsByHotel(hotel);
        
        this.cutAllHotelCommentsAssignments(hotel);
        
        this.cutAllHotelSpecificitysAssignments(hotel);
        
        this.cutAllHotelRoomsAssignments(hotel);
        
    }

    // Remove all assignments from all comment a hotel. Called before delete a hotel.
    @Transactional
    private void cutAllHotelCommentsAssignments(HotelEntity hotel) {
        entityManager
                .createQuery("UPDATE UserComment c SET c.hotel = NULL WHERE c.hotel = :p")
                .setParameter("p", hotel).executeUpdate();
    }
    
    // Remove all assignments from all specificity a hotel. Called before delete a hotel.
    @Transactional
    private void cutAllHotelSpecificitysAssignments(HotelEntity hotel) {
        entityManager
                .createQuery("UPDATE Specificity c SET c.hotel = NULL WHERE c.hotel = :p")
                .setParameter("p", hotel).executeUpdate();
    }
    
    // Remove all assignments from all room a hotel. Called before delete a hotel.
    @Transactional
    private void cutAllHotelRoomsAssignments(HotelEntity hotel) {
        entityManager
                .createQuery("UPDATE Room c SET c.hotel = NULL WHERE c.hotel = :p")
                .setParameter("p", hotel).executeUpdate();
    }
    
    @Transactional
    public List<HotelEntity> findAvailableHotels(HotelierEntity hotelier) {
        return entityManager.createQuery("SELECT o FROM Hotel o where o.id not in (select o.id from Hotel o join o.hoteliers p where p = :p)", HotelEntity.class).setParameter("p", hotelier).getResultList();
    }

    @Transactional
    public List<HotelEntity> findHotelsByHotelier(HotelierEntity hotelier) {
        return entityManager.createQuery("SELECT o FROM Hotel o where o.id in (select o.id from Hotel o join o.hoteliers p where p = :p)", HotelEntity.class).setParameter("p", hotelier).getResultList();
    }

    @Transactional
    public HotelEntity fetchHoteliers(HotelEntity hotel) {
        hotel = find(hotel.getId());
        hotel.getHoteliers().size();
        return hotel;
    }
    
    @Transactional
    public HotelEntity lazilyLoadImageToHotel(HotelEntity hotel) {
        PersistenceUnitUtil u = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
        if (!u.isLoaded(hotel, "image") && hotel.getId() != null) {
            hotel = find(hotel.getId());
            hotel.getImage().getId();
        }
        return hotel;
    }
    
}
