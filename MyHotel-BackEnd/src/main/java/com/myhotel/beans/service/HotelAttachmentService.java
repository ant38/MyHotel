package com.myhotel.beans.service;

import com.myhotel.beans.domain.HotelAttachment;
import com.myhotel.beans.domain.HotelEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class HotelAttachmentService extends BaseService<HotelAttachment> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public HotelAttachmentService(){
        super(HotelAttachment.class);
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM HotelAttachment o", Long.class).getSingleResult();
    }

    @Transactional
    public void deleteAttachmentsByHotel(HotelEntity hotel) {
        entityManager
                .createQuery("DELETE FROM HotelAttachment c WHERE c.hotel = :p")
                .setParameter("p", hotel).executeUpdate();
    }
    
    @Transactional
    public List<HotelAttachment> getAttachmentsList(HotelEntity hotel) {
        if (hotel == null || hotel.getId() == null) {
            return new ArrayList<>();
        }
        // The byte streams are not loaded from database with following line. This would cost too much.
        return entityManager.createQuery("SELECT NEW com.myhotel.beans.domain.HotelAttachment(o.id, o.fileName) FROM HotelAttachment o WHERE o.hotel.id = :id", HotelAttachment.class).setParameter("id", hotel.getId()).getResultList();
    }
}
