package com.myhotel.beans.web;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.service.BookingService;
import com.myhotel.beans.service.ClientService;
import com.myhotel.beans.service.RoomService;
import com.myhotel.beans.service.security.SecurityWrapper;
import com.myhotel.beans.web.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@Named("bookingBean")
@ViewScoped
public class BookingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(BookingBean.class.getName());
    
    private List<BookingEntity> bookingList;

    private BookingEntity booking;
    
    @Inject
    private BookingService bookingService;
    
    @Inject
    private RoomService roomService;
    
    @Inject
    private ClientService clientService;
    
    private DualListModel<RoomEntity> rooms;
    private List<String> transferedRoomIDs;
    private List<String> removedRoomIDs;
    
    private DualListModel<ClientEntity> clients;
    private List<String> transferedClientIDs;
    private List<String> removedClientIDs;
    
    public void prepareNewBooking() {
        reset();
        this.booking = new BookingEntity();
        // set any default values now, if you need
        // Example: this.booking.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (booking.getId() != null) {
                booking = bookingService.update(booking);
                message = "message_successfully_updated";
            } else {
                booking = bookingService.save(booking);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        bookingList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            bookingService.delete(booking);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(BookingEntity booking) {
        reset();
        this.booking = booking;
    }
    
    public void reset() {
        booking = null;
        bookingList = null;
        
        rooms = null;
        transferedRoomIDs = null;
        removedRoomIDs = null;
        
        clients = null;
        transferedClientIDs = null;
        removedClientIDs = null;
        
    }

    public DualListModel<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(DualListModel<RoomEntity> rooms) {
        this.rooms = rooms;
    }
    
    public List<RoomEntity> getFullRoomsList() {
        List<RoomEntity> allList = new ArrayList<>();
        allList.addAll(rooms.getSource());
        allList.addAll(rooms.getTarget());
        return allList;
    }
    
    public void onRoomsDialog(BookingEntity booking) {
        // Prepare the room PickList
        this.booking = booking;
        List<RoomEntity> selectedRoomsFromDB = roomService
                .findRoomsByBooking(this.booking);
        List<RoomEntity> availableRoomsFromDB = roomService
                .findAvailableRooms(this.booking);
        this.rooms = new DualListModel<>(availableRoomsFromDB, selectedRoomsFromDB);
        
        transferedRoomIDs = new ArrayList<>();
        removedRoomIDs = new ArrayList<>();
    }
    
    public void onRoomsPickListTransfer(TransferEvent event) {
        // If a room is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((RoomEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedRoomIDs.add(id);
                removedRoomIDs.remove(id);
            } else if (event.isRemove()) {
                removedRoomIDs.add(id);
                transferedRoomIDs.remove(id);
            }
        }
        
    }
    
    public void updateRoom(RoomEntity room) {
        // If a new room is created, we persist it to the database,
        // but we do not assign it to this booking in the database, yet.
        rooms.getTarget().add(room);
        transferedRoomIDs.add(room.getId().toString());
    }
    
    public DualListModel<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(DualListModel<ClientEntity> clients) {
        this.clients = clients;
    }
    
    public List<ClientEntity> getFullClientsList() {
        List<ClientEntity> allList = new ArrayList<>();
        allList.addAll(clients.getSource());
        allList.addAll(clients.getTarget());
        return allList;
    }
    
    public void onClientsDialog(BookingEntity booking) {
        // Prepare the client PickList
        this.booking = booking;
        List<ClientEntity> selectedClientsFromDB = clientService
                .findClientsByBooking(this.booking);
        List<ClientEntity> availableClientsFromDB = clientService
                .findAvailableClients(this.booking);
        this.clients = new DualListModel<>(availableClientsFromDB, selectedClientsFromDB);
        
        transferedClientIDs = new ArrayList<>();
        removedClientIDs = new ArrayList<>();
    }
    
    public void onClientsPickListTransfer(TransferEvent event) {
        // If a client is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((ClientEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedClientIDs.add(id);
                removedClientIDs.remove(id);
            } else if (event.isRemove()) {
                removedClientIDs.add(id);
                transferedClientIDs.remove(id);
            }
        }
        
    }
    
    public void updateClient(ClientEntity client) {
        // If a new client is created, we persist it to the database,
        // but we do not assign it to this booking in the database, yet.
        clients.getTarget().add(client);
        transferedClientIDs.add(client.getId().toString());
    }
    
    public void onRoomsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<RoomEntity> selectedRoomsFromDB = roomService.findRoomsByBooking(this.booking);
            List<RoomEntity> availableRoomsFromDB = roomService.findAvailableRooms(this.booking);

            // Because rooms are lazily loaded, we need to fetch them now
            this.booking = bookingService.fetchRooms(this.booking);
            
            for (RoomEntity room : selectedRoomsFromDB) {
                if (removedRoomIDs.contains(room.getId().toString())) {
                    
                    this.booking.getRooms().remove(room);
                    
                }
            }
    
            for (RoomEntity room : availableRoomsFromDB) {
                if (transferedRoomIDs.contains(room.getId().toString())) {
                    
                    this.booking.getRooms().add(room);
                    
                }
            }
            
            this.booking = bookingService.update(this.booking);
            
            FacesMessage facesMessage = MessageFactory.getMessage("message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void onClientsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<ClientEntity> selectedClientsFromDB = clientService.findClientsByBooking(this.booking);
            List<ClientEntity> availableClientsFromDB = clientService.findAvailableClients(this.booking);

            // Because clients are lazily loaded, we need to fetch them now
            this.booking = bookingService.fetchClients(this.booking);
            
            for (ClientEntity client : selectedClientsFromDB) {
                if (removedClientIDs.contains(client.getId().toString())) {
                    
                    this.booking.getClients().remove(client);
                    
                }
            }
    
            for (ClientEntity client : availableClientsFromDB) {
                if (transferedClientIDs.contains(client.getId().toString())) {
                    
                    this.booking.getClients().add(client);
                    
                }
            }
            
            this.booking = bookingService.update(this.booking);
            
            FacesMessage facesMessage = MessageFactory.getMessage("message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public BookingEntity getBooking() {
        if (this.booking == null) {
            prepareNewBooking();
        }
        return this.booking;
    }
    
    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }
    
    public List<BookingEntity> getBookingList() {
        if (bookingList == null) {
            bookingList = bookingService.findAllBookingEntities();
        }
        return bookingList;
    }

    public void setBookingList(List<BookingEntity> bookingList) {
        this.bookingList = bookingList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(BookingEntity booking, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
