package com.myhotel.beans.service;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.domain.SaveEntity;

import org.joda.time.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Named
public class OfferService extends BaseService<OfferEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Inject
	private HotelService hotelService;
	@Inject
	private RoomService roomService;
    
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
    
    @Transactional
    public List<OfferEntity> getOffers(String city, Long adults, Long children, Date dateIn, Date dateOut, Long days) {
    	List<OfferEntity> offers = new ArrayList<>();
    	DateTime dateTimeIn = new DateTime(dateIn);
    	DateTime dateTimeOut = new DateTime(dateOut);
		
		if(days <= Days.daysBetween(dateTimeIn, dateTimeOut).getDays()) {
			List<HotelEntity> hotels = hotelService.findHotelsByCity(city);
			DateTime dateTimeStart = dateTimeIn;
			DateTime dateTimeEnd;
			if(days < 1) {
				dateTimeEnd = dateTimeOut;
				days = new Long(Days.daysBetween(dateTimeIn, dateTimeOut).getDays());
			} else {
				dateTimeEnd = dateTimeStart.plusDays(days.intValue());
			}
			
			while(!dateTimeEnd.isAfter(dateTimeOut)) {
				List<RoomEntity> rooms = roomService.findRooms(hotels, adults, children, dateTimeStart.toDate(), dateTimeEnd.toDate());
				
				for(int i=0; i<rooms.size(); i++) {
					OfferEntity offer = new OfferEntity();
					offer.setDateStart(dateTimeStart.toDate());
					offer.setDateEnd(dateTimeEnd.toDate());
					RoomEntity room = roomService.find(rooms.get(i).getId());
					offer.setPrice(room.getPrice() * days);
					boolean continueFor = false;
					for(int j=0; j<offers.size(); j++) {
						if(offers.get(j).getRooms().contains(room)) {
							continueFor = true;
						}
					}
					if(continueFor) {
						continue;
					}
					room.getOffers().add(offer);
					offer.getRooms().add(room);
					entityManager.persist(offer);
					offers.add(offer);
				}
				
				dateTimeStart = dateTimeStart.plusDays(1);
				dateTimeEnd = dateTimeEnd.plusDays(1);
			}
		}
		
		return offers;
    }
    
}
