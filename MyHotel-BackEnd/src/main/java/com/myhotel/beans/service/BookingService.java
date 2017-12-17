package com.myhotel.beans.service;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.config.MailConfig;
import com.myhotel.config.SMTPAuthenticator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Named
public class BookingService extends BaseService<BookingEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Inject
	private ClientService clientService;
    
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
    
    @Transactional
    public BookingEntity book(OfferEntity offer, Long clientId, Long paid) {
		BookingEntity booking = new BookingEntity();
		List<ClientEntity> clients = new ArrayList<>();
		ClientEntity client = clientService.find(clientId);
		entityManager.detach(client);
		clients.add(client);
		booking.setClients(clients);
		client.getBookings().add(booking);
		booking.setDateIn(offer.getDateStart());
		booking.setDateOut(offer.getDateEnd());
		booking.setPaid(paid > 0);
		List<RoomEntity> rooms = offer.getRooms();
		for(int i=0; i<rooms.size(); i++) {
			RoomEntity room = rooms.get(i);
			room.getBookings().add(booking);
			entityManager.detach(room);
			booking.getRooms().add(room);
		}
		entityManager.persist(booking);
		sendMail(booking.getId());
		return booking;
    }
    
    @Transactional
    public String sendMail(Long id) {
    	try {
    		BookingEntity booking = find(id);
    		List<RoomEntity> rooms = booking.getRooms();
    		HotelEntity hotel = rooms.get(0).getHotel();
    		ClientEntity client = booking.getClients().get(0);
    		
    		Properties props = new Properties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.host", MailConfig.HOST);
	        props.put("mail.smtp.auth", "true");

	        Authenticator auth = new SMTPAuthenticator();
	        Session mailSession = Session.getDefaultInstance(props, auth);
	        // uncomment for debugging infos to stdout
	        mailSession.setDebug(true);
	        Transport transport = mailSession.getTransport();

	        MimeMessage message = new MimeMessage(mailSession);
	        message.setSubject("Votre réservation sur MyHotel");
	        String content = "<p>Votre réservation à l'hotel " + hotel.getName() + " a bien été enregistrée.</p>";
	        content += "<p>Voici votre QR code à présenter à la réception :</p>";
	        content += "<img src=\"https://api.qrserver.com/v1/create-qr-code/?data=" + id + "\">";
	        message.setContent(content, "text/html; charset=\"UTF-8\"");
	        message.setFrom(new InternetAddress(MailConfig.FROM, "MyHotel"));
	        message.addRecipient(Message.RecipientType.TO,
        		 new InternetAddress(client.getEmail()));

	        transport.connect();
	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        transport.close();
	        
	        return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
    }
    
}
