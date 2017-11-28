package com.myhotel.beans.service;

import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.SaveEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class SaveService extends BaseService<SaveEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public SaveService(){
        super(SaveEntity.class);
    }
    
    @Transactional
    public List<SaveEntity> findAllSaveEntities() {
        
        return entityManager.createQuery("SELECT o FROM Save o ", SaveEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Save o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(SaveEntity save) {

        /* This is called before a Save is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    @Transactional
    public List<SaveEntity> findAvailableSaves(ClientEntity client) {
        return entityManager.createQuery("SELECT o FROM Save o WHERE o.client IS NULL", SaveEntity.class).getResultList();
    }

    @Transactional
    public List<SaveEntity> findSavesByClient(ClientEntity client) {
        return entityManager.createQuery("SELECT o FROM Save o WHERE o.client = :client", SaveEntity.class).setParameter("client", client).getResultList();
    }

    @Transactional
    public List<SaveEntity> findAvailableSaves(OfferEntity offer) {
        return entityManager.createQuery("SELECT o FROM Save o where o.id not in (select o.id from Save o join o.offers p where p = :p)", SaveEntity.class).setParameter("p", offer).getResultList();
    }

    @Transactional
    public List<SaveEntity> findSavesByOffer(OfferEntity offer) {
        return entityManager.createQuery("SELECT o FROM Save o where o.id in (select o.id from Save o join o.offers p where p = :p)", SaveEntity.class).setParameter("p", offer).getResultList();
    }

    @Transactional
    public SaveEntity fetchOffers(SaveEntity save) {
        save = find(save.getId());
        save.getOffers().size();
        return save;
    }
    
}
