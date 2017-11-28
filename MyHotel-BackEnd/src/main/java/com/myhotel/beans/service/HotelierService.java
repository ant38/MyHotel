package com.myhotel.beans.service;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.HotelierEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class HotelierService extends BaseService<HotelierEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public HotelierService(){
        super(HotelierEntity.class);
    }
    
    @Transactional
    public List<HotelierEntity> findAllHotelierEntities() {
        
        return entityManager.createQuery("SELECT o FROM Hotelier o ", HotelierEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Hotelier o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(HotelierEntity hotelier) {

        /* This is called before a Hotelier is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    @Transactional
    public List<HotelierEntity> findAvailableHoteliers(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM Hotelier o where o.id not in (select o.id from Hotelier o join o.hotels p where p = :p)", HotelierEntity.class).setParameter("p", hotel).getResultList();
    }

    @Transactional
    public List<HotelierEntity> findHoteliersByHotel(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM Hotelier o where o.id in (select o.id from Hotelier o join o.hotels p where p = :p)", HotelierEntity.class).setParameter("p", hotel).getResultList();
    }

    @Transactional
    public HotelierEntity fetchHotels(HotelierEntity hotelier) {
        hotelier = find(hotelier.getId());
        hotelier.getHotels().size();
        return hotelier;
    }
    
}
