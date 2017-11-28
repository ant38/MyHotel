package com.myhotel.beans.service;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.SpecificityEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class SpecificityService extends BaseService<SpecificityEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public SpecificityService(){
        super(SpecificityEntity.class);
    }
    
    @Transactional
    public List<SpecificityEntity> findAllSpecificityEntities() {
        
        return entityManager.createQuery("SELECT o FROM Specificity o ", SpecificityEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Specificity o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(SpecificityEntity specificity) {

        /* This is called before a Specificity is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    @Transactional
    public List<SpecificityEntity> findAvailableSpecificitys(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM Specificity o WHERE o.hotel IS NULL", SpecificityEntity.class).getResultList();
    }

    @Transactional
    public List<SpecificityEntity> findSpecificitysByHotel(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM Specificity o WHERE o.hotel = :hotel", SpecificityEntity.class).setParameter("hotel", hotel).getResultList();
    }

}
