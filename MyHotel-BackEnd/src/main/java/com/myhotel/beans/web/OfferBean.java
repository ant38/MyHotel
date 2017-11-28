package com.myhotel.beans.web;

import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.service.OfferService;
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

@Named("offerBean")
@ViewScoped
public class OfferBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(OfferBean.class.getName());
    
    private List<OfferEntity> offerList;

    private OfferEntity offer;
    
    @Inject
    private OfferService offerService;
    
    @Inject
    private RoomService roomService;
    
    private DualListModel<RoomEntity> rooms;
    private List<String> transferedRoomIDs;
    private List<String> removedRoomIDs;
    
    public void prepareNewOffer() {
        reset();
        this.offer = new OfferEntity();
        // set any default values now, if you need
        // Example: this.offer.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (offer.getId() != null) {
                offer = offerService.update(offer);
                message = "message_successfully_updated";
            } else {
                offer = offerService.save(offer);
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
        
        offerList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            offerService.delete(offer);
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
    
    public void onDialogOpen(OfferEntity offer) {
        reset();
        this.offer = offer;
    }
    
    public void reset() {
        offer = null;
        offerList = null;
        
        rooms = null;
        transferedRoomIDs = null;
        removedRoomIDs = null;
        
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
    
    public void onRoomsDialog(OfferEntity offer) {
        // Prepare the room PickList
        this.offer = offer;
        List<RoomEntity> selectedRoomsFromDB = roomService
                .findRoomsByOffer(this.offer);
        List<RoomEntity> availableRoomsFromDB = roomService
                .findAvailableRooms(this.offer);
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
        // but we do not assign it to this offer in the database, yet.
        rooms.getTarget().add(room);
        transferedRoomIDs.add(room.getId().toString());
    }
    
    public void onRoomsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<RoomEntity> selectedRoomsFromDB = roomService.findRoomsByOffer(this.offer);
            List<RoomEntity> availableRoomsFromDB = roomService.findAvailableRooms(this.offer);

            for (RoomEntity room : selectedRoomsFromDB) {
                if (removedRoomIDs.contains(room.getId().toString())) {
                    
                    // Because offers are lazy loaded, we need to fetch them now
                    room = roomService.fetchOffers(room);
                    room.getOffers().remove(offer);
                    roomService.update(room);
                    
                }
            }
    
            for (RoomEntity room : availableRoomsFromDB) {
                if (transferedRoomIDs.contains(room.getId().toString())) {
                    
                    // Because offers are lazy loaded, we need to fetch them now
                    room = roomService.fetchOffers(room);
                    room.getOffers().add(offer);
                    roomService.update(room);
                    
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
    
    public OfferEntity getOffer() {
        if (this.offer == null) {
            prepareNewOffer();
        }
        return this.offer;
    }
    
    public void setOffer(OfferEntity offer) {
        this.offer = offer;
    }
    
    public List<OfferEntity> getOfferList() {
        if (offerList == null) {
            offerList = offerService.findAllOfferEntities();
        }
        return offerList;
    }

    public void setOfferList(List<OfferEntity> offerList) {
        this.offerList = offerList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(OfferEntity offer, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
