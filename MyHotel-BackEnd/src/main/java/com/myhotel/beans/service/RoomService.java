package com.myhotel.beans.service;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class RoomService extends BaseService<RoomEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    BookingService bookingService;
    
    public RoomService(){
        super(RoomEntity.class);
    }
    
    @Transactional
    public List<RoomEntity> findAllRoomEntities() {
        
        return entityManager.createQuery("SELECT o FROM Room o ", RoomEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Room o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(RoomEntity room) {

        /* This is called before a Room is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    @Transactional
    public List<RoomEntity> findAvailableRooms(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM Room o WHERE o.hotel IS NULL", RoomEntity.class).getResultList();
    }

    @Transactional
    public List<RoomEntity> findRoomsByHotel(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM Room o WHERE o.hotel = :hotel", RoomEntity.class).setParameter("hotel", hotel).getResultList();
    }

    @Transactional
    public List<RoomEntity> findAvailableRooms(OfferEntity offer) {
        return entityManager.createQuery("SELECT o FROM Room o where o.id not in (select o.id from Room o join o.offers p where p = :p)", RoomEntity.class).setParameter("p", offer).getResultList();
    }

    @Transactional
    public List<RoomEntity> findRoomsByOffer(OfferEntity offer) {
        return entityManager.createQuery("SELECT o FROM Room o where o.id in (select o.id from Room o join o.offers p where p = :p)", RoomEntity.class).setParameter("p", offer).getResultList();
    }

    @Transactional
    public List<RoomEntity> findAvailableRooms(BookingEntity booking) {
        return entityManager.createQuery("SELECT o FROM Room o where o.id not in (select o.id from Room o join o.bookings p where p = :p)", RoomEntity.class).setParameter("p", booking).getResultList();
    }

    @Transactional
    public List<RoomEntity> findRoomsByBooking(BookingEntity booking) {
        return entityManager.createQuery("SELECT o FROM Room o where o.id in (select o.id from Room o join o.bookings p where p = :p)", RoomEntity.class).setParameter("p", booking).getResultList();
    }

    @Transactional
    public RoomEntity fetchOffers(RoomEntity room) {
        room = find(room.getId());
        room.getOffers().size();
        return room;
    }
    
    @Transactional
    public RoomEntity fetchBookings(RoomEntity room) {
        room = find(room.getId());
        room.getBookings().size();
        return room;
    }
    
    private boolean isAvailable(RoomEntity room, Date dateIn, Date dateOut) {
    	List<BookingEntity> bookings = bookingService.findBookingsByRoom(room);
    	
    	for(int i=0; i<bookings.size(); i++) {
    		if(dateOut.after(bookings.get(i).getDateIn()) && dateIn.before(bookings.get(i).getDateOut())) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    @Transactional
    public List<RoomEntity> findRooms(List<HotelEntity> hotels, Long adults, Long children, Date dateIn, Date dateOut) {
    	if(hotels.size() < 1) {
    		return new ArrayList<RoomEntity>();
    	}
    	
    	List<RoomEntity> rooms = entityManager
    			.createQuery("SELECT o FROM Room o WHERE o.hotel IN :hotels AND places >= :beds")
    			.setParameter("hotels", hotels)
    			.setParameter("beds", (int)(adults+children))
    			.getResultList();
    	
    	List<RoomEntity> availableRooms = new ArrayList<>();
    	for (int i = 0; i < rooms.size(); i++) {
			if(isAvailable(rooms.get(i), dateIn, dateOut)) {
				availableRooms.add(rooms.get(i));
			}
		}
    	
    	return availableRooms;
    }
    
}
