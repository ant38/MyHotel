package com.myhotel.beans.service;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.RoomEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class BookingService extends BaseService<BookingEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public BookingService(){
        super(BookingEntity.class);
    }
    
    @Transactional
    public List<BookingEntity> findAllBookingEntities() {
        
        return entityManager.createQuery("SELECT o FROM Booking o ", BookingEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Booking o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(BookingEntity booking) {

        /* This is called before a Booking is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    @Transactional
    public List<BookingEntity> findAvailableBookings(RoomEntity room) {
        return entityManager.createQuery("SELECT o FROM Booking o where o.id not in (select o.id from Booking o join o.rooms p where p = :p)", BookingEntity.class).setParameter("p", room).getResultList();
    }

    @Transactional
    public List<BookingEntity> findBookingsByRoom(RoomEntity room) {
        return entityManager.createQuery("SELECT o FROM Booking o where o.id in (select o.id from Booking o join o.rooms p where p = :p)", BookingEntity.class).setParameter("p", room).getResultList();
    }

    @Transactional
    public List<BookingEntity> findAvailableBookings(ClientEntity client) {
        return entityManager.createQuery("SELECT o FROM Booking o where o.id not in (select o.id from Booking o join o.clients p where p = :p)", BookingEntity.class).setParameter("p", client).getResultList();
    }

    @Transactional
    public List<BookingEntity> findBookingsByClient(ClientEntity client) {
        return entityManager.createQuery("SELECT o FROM Booking o where o.id in (select o.id from Booking o join o.clients p where p = :p)", BookingEntity.class).setParameter("p", client).getResultList();
    }

    @Transactional
    public BookingEntity fetchRooms(BookingEntity booking) {
        booking = find(booking.getId());
        booking.getRooms().size();
        return booking;
    }
    
    @Transactional
    public BookingEntity fetchClients(BookingEntity booking) {
        booking = find(booking.getId());
        booking.getClients().size();
        return booking;
    }
    
}
