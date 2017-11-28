package com.myhotel.beans.web;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.SaveEntity;
import com.myhotel.beans.domain.UserCommentEntity;
import com.myhotel.beans.service.BookingService;
import com.myhotel.beans.service.ClientService;
import com.myhotel.beans.service.SaveService;
import com.myhotel.beans.service.UserCommentService;
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

@Named("clientBean")
@ViewScoped
public class ClientBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ClientBean.class.getName());
    
    private List<ClientEntity> clientList;

    private ClientEntity client;
    
    @Inject
    private ClientService clientService;
    
    @Inject
    private UserCommentService userCommentService;
    
    @Inject
    private BookingService bookingService;
    
    @Inject
    private SaveService saveService;
    
    private DualListModel<UserCommentEntity> comments;
    private List<String> transferedCommentIDs;
    private List<String> removedCommentIDs;
    
    private DualListModel<BookingEntity> bookings;
    private List<String> transferedBookingIDs;
    private List<String> removedBookingIDs;
    
    private DualListModel<SaveEntity> saves;
    private List<String> transferedSaveIDs;
    private List<String> removedSaveIDs;
    
    public void prepareNewClient() {
        reset();
        this.client = new ClientEntity();
        // set any default values now, if you need
        // Example: this.client.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (client.getId() != null) {
                client = clientService.update(client);
                message = "message_successfully_updated";
            } else {
                client = clientService.save(client);
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
        
        clientList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            clientService.delete(client);
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
    
    public void onDialogOpen(ClientEntity client) {
        reset();
        this.client = client;
    }
    
    public void reset() {
        client = null;
        clientList = null;
        
        comments = null;
        transferedCommentIDs = null;
        removedCommentIDs = null;
        
        bookings = null;
        transferedBookingIDs = null;
        removedBookingIDs = null;
        
        saves = null;
        transferedSaveIDs = null;
        removedSaveIDs = null;
        
    }

    public DualListModel<UserCommentEntity> getComments() {
        return comments;
    }

    public void setComments(DualListModel<UserCommentEntity> userComments) {
        this.comments = userComments;
    }
    
    public List<UserCommentEntity> getFullCommentsList() {
        List<UserCommentEntity> allList = new ArrayList<>();
        allList.addAll(comments.getSource());
        allList.addAll(comments.getTarget());
        return allList;
    }
    
    public void onCommentsDialog(ClientEntity client) {
        // Prepare the comment PickList
        this.client = client;
        List<UserCommentEntity> selectedUserCommentsFromDB = userCommentService
                .findCommentsByClient(this.client);
        List<UserCommentEntity> availableUserCommentsFromDB = userCommentService
                .findAvailableComments(this.client);
        this.comments = new DualListModel<>(availableUserCommentsFromDB, selectedUserCommentsFromDB);
        
        transferedCommentIDs = new ArrayList<>();
        removedCommentIDs = new ArrayList<>();
    }
    
    public void onCommentsPickListTransfer(TransferEvent event) {
        // If a comment is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((UserCommentEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedCommentIDs.add(id);
                removedCommentIDs.remove(id);
            } else if (event.isRemove()) {
                removedCommentIDs.add(id);
                transferedCommentIDs.remove(id);
            }
        }
        
    }
    
    public void updateComment(UserCommentEntity userComment) {
        // If a new comment is created, we persist it to the database,
        // but we do not assign it to this client in the database, yet.
        comments.getTarget().add(userComment);
        transferedCommentIDs.add(userComment.getId().toString());
    }
    
    public DualListModel<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(DualListModel<BookingEntity> bookings) {
        this.bookings = bookings;
    }
    
    public List<BookingEntity> getFullBookingsList() {
        List<BookingEntity> allList = new ArrayList<>();
        allList.addAll(bookings.getSource());
        allList.addAll(bookings.getTarget());
        return allList;
    }
    
    public void onBookingsDialog(ClientEntity client) {
        // Prepare the booking PickList
        this.client = client;
        List<BookingEntity> selectedBookingsFromDB = bookingService
                .findBookingsByClient(this.client);
        List<BookingEntity> availableBookingsFromDB = bookingService
                .findAvailableBookings(this.client);
        this.bookings = new DualListModel<>(availableBookingsFromDB, selectedBookingsFromDB);
        
        transferedBookingIDs = new ArrayList<>();
        removedBookingIDs = new ArrayList<>();
    }
    
    public void onBookingsPickListTransfer(TransferEvent event) {
        // If a booking is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((BookingEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedBookingIDs.add(id);
                removedBookingIDs.remove(id);
            } else if (event.isRemove()) {
                removedBookingIDs.add(id);
                transferedBookingIDs.remove(id);
            }
        }
        
    }
    
    public void updateBooking(BookingEntity booking) {
        // If a new booking is created, we persist it to the database,
        // but we do not assign it to this client in the database, yet.
        bookings.getTarget().add(booking);
        transferedBookingIDs.add(booking.getId().toString());
    }
    
    public DualListModel<SaveEntity> getSaves() {
        return saves;
    }

    public void setSaves(DualListModel<SaveEntity> saves) {
        this.saves = saves;
    }
    
    public List<SaveEntity> getFullSavesList() {
        List<SaveEntity> allList = new ArrayList<>();
        allList.addAll(saves.getSource());
        allList.addAll(saves.getTarget());
        return allList;
    }
    
    public void onSavesDialog(ClientEntity client) {
        // Prepare the save PickList
        this.client = client;
        List<SaveEntity> selectedSavesFromDB = saveService
                .findSavesByClient(this.client);
        List<SaveEntity> availableSavesFromDB = saveService
                .findAvailableSaves(this.client);
        this.saves = new DualListModel<>(availableSavesFromDB, selectedSavesFromDB);
        
        transferedSaveIDs = new ArrayList<>();
        removedSaveIDs = new ArrayList<>();
    }
    
    public void onSavesPickListTransfer(TransferEvent event) {
        // If a save is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((SaveEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedSaveIDs.add(id);
                removedSaveIDs.remove(id);
            } else if (event.isRemove()) {
                removedSaveIDs.add(id);
                transferedSaveIDs.remove(id);
            }
        }
        
    }
    
    public void updateSave(SaveEntity save) {
        // If a new save is created, we persist it to the database,
        // but we do not assign it to this client in the database, yet.
        saves.getTarget().add(save);
        transferedSaveIDs.add(save.getId().toString());
    }
    
    public void onCommentsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            List<UserCommentEntity> selectedUserCommentsFromDB = userCommentService
                    .findCommentsByClient(this.client);
            List<UserCommentEntity> availableUserCommentsFromDB = userCommentService
                    .findAvailableComments(this.client);
            
            for (UserCommentEntity userComment : selectedUserCommentsFromDB) {
                if (removedCommentIDs.contains(userComment.getId().toString())) {
                    userComment.setClient(null);
                    userCommentService.update(userComment);
                }
            }
    
            for (UserCommentEntity userComment : availableUserCommentsFromDB) {
                if (transferedCommentIDs.contains(userComment.getId().toString())) {
                    userComment.setClient(client);
                    userCommentService.update(userComment);
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void onSavesSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            List<SaveEntity> selectedSavesFromDB = saveService
                    .findSavesByClient(this.client);
            List<SaveEntity> availableSavesFromDB = saveService
                    .findAvailableSaves(this.client);
            
            for (SaveEntity save : selectedSavesFromDB) {
                if (removedSaveIDs.contains(save.getId().toString())) {
                    save.setClient(null);
                    saveService.update(save);
                }
            }
    
            for (SaveEntity save : availableSavesFromDB) {
                if (transferedSaveIDs.contains(save.getId().toString())) {
                    save.setClient(client);
                    saveService.update(save);
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void onBookingsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<BookingEntity> selectedBookingsFromDB = bookingService.findBookingsByClient(this.client);
            List<BookingEntity> availableBookingsFromDB = bookingService.findAvailableBookings(this.client);

            for (BookingEntity booking : selectedBookingsFromDB) {
                if (removedBookingIDs.contains(booking.getId().toString())) {
                    
                    // Because clients are lazy loaded, we need to fetch them now
                    booking = bookingService.fetchClients(booking);
                    booking.getClients().remove(client);
                    bookingService.update(booking);
                    
                }
            }
    
            for (BookingEntity booking : availableBookingsFromDB) {
                if (transferedBookingIDs.contains(booking.getId().toString())) {
                    
                    // Because clients are lazy loaded, we need to fetch them now
                    booking = bookingService.fetchClients(booking);
                    booking.getClients().add(client);
                    bookingService.update(booking);
                    
                }
            }
            
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
    
    public ClientEntity getClient() {
        if (this.client == null) {
            prepareNewClient();
        }
        return this.client;
    }
    
    public void setClient(ClientEntity client) {
        this.client = client;
    }
    
    public List<ClientEntity> getClientList() {
        if (clientList == null) {
            clientList = clientService.findAllClientEntities();
        }
        return clientList;
    }

    public void setClientList(List<ClientEntity> clientList) {
        this.clientList = clientList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(ClientEntity client, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
