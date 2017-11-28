package com.myhotel.beans.web;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.HotelierEntity;
import com.myhotel.beans.domain.HotelierStatus;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.HotelierService;
import com.myhotel.beans.service.security.SecurityWrapper;
import com.myhotel.beans.web.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@Named("hotelierBean")
@ViewScoped
public class HotelierBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(HotelierBean.class.getName());
    
    private List<HotelierEntity> hotelierList;

    private HotelierEntity hotelier;
    
    @Inject
    private HotelierService hotelierService;
    
    @Inject
    private HotelService hotelService;
    
    private DualListModel<HotelEntity> hotels;
    private List<String> transferedHotelIDs;
    private List<String> removedHotelIDs;
    
    public void prepareNewHotelier() {
        reset();
        this.hotelier = new HotelierEntity();
        // set any default values now, if you need
        // Example: this.hotelier.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (hotelier.getId() != null) {
                hotelier = hotelierService.update(hotelier);
                message = "message_successfully_updated";
            } else {
                hotelier = hotelierService.save(hotelier);
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
        
        hotelierList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            hotelierService.delete(hotelier);
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
    
    public void onDialogOpen(HotelierEntity hotelier) {
        reset();
        this.hotelier = hotelier;
    }
    
    public void reset() {
        hotelier = null;
        hotelierList = null;
        
        hotels = null;
        transferedHotelIDs = null;
        removedHotelIDs = null;
        
    }

    public DualListModel<HotelEntity> getHotels() {
        return hotels;
    }

    public void setHotels(DualListModel<HotelEntity> hotels) {
        this.hotels = hotels;
    }
    
    public List<HotelEntity> getFullHotelsList() {
        List<HotelEntity> allList = new ArrayList<>();
        allList.addAll(hotels.getSource());
        allList.addAll(hotels.getTarget());
        return allList;
    }
    
    public void onHotelsDialog(HotelierEntity hotelier) {
        // Prepare the hotel PickList
        this.hotelier = hotelier;
        List<HotelEntity> selectedHotelsFromDB = hotelService
                .findHotelsByHotelier(this.hotelier);
        List<HotelEntity> availableHotelsFromDB = hotelService
                .findAvailableHotels(this.hotelier);
        this.hotels = new DualListModel<>(availableHotelsFromDB, selectedHotelsFromDB);
        
        transferedHotelIDs = new ArrayList<>();
        removedHotelIDs = new ArrayList<>();
    }
    
    public void onHotelsPickListTransfer(TransferEvent event) {
        // If a hotel is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((HotelEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedHotelIDs.add(id);
                removedHotelIDs.remove(id);
            } else if (event.isRemove()) {
                removedHotelIDs.add(id);
                transferedHotelIDs.remove(id);
            }
        }
        
    }
    
    public void updateHotel(HotelEntity hotel) {
        // If a new hotel is created, we persist it to the database,
        // but we do not assign it to this hotelier in the database, yet.
        hotels.getTarget().add(hotel);
        transferedHotelIDs.add(hotel.getId().toString());
    }
    
    public void onHotelsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<HotelEntity> selectedHotelsFromDB = hotelService.findHotelsByHotelier(this.hotelier);
            List<HotelEntity> availableHotelsFromDB = hotelService.findAvailableHotels(this.hotelier);

            // Because hotels are lazily loaded, we need to fetch them now
            this.hotelier = hotelierService.fetchHotels(this.hotelier);
            
            for (HotelEntity hotel : selectedHotelsFromDB) {
                if (removedHotelIDs.contains(hotel.getId().toString())) {
                    
                    this.hotelier.getHotels().remove(hotel);
                    
                }
            }
    
            for (HotelEntity hotel : availableHotelsFromDB) {
                if (transferedHotelIDs.contains(hotel.getId().toString())) {
                    
                    this.hotelier.getHotels().add(hotel);
                    
                }
            }
            
            this.hotelier = hotelierService.update(this.hotelier);
            
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
    
    public SelectItem[] getStatusSelectItems() {
        SelectItem[] items = new SelectItem[HotelierStatus.values().length];

        int i = 0;
        for (HotelierStatus status : HotelierStatus.values()) {
            items[i++] = new SelectItem(status, getLabelForStatus(status));
        }
        return items;
    }
    
    public String getLabelForStatus(HotelierStatus value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_hotelier_status_" + value);
        return label == null? value.toString() : label;
    }
    
    public HotelierEntity getHotelier() {
        if (this.hotelier == null) {
            prepareNewHotelier();
        }
        return this.hotelier;
    }
    
    public void setHotelier(HotelierEntity hotelier) {
        this.hotelier = hotelier;
    }
    
    public List<HotelierEntity> getHotelierList() {
        if (hotelierList == null) {
            hotelierList = hotelierService.findAllHotelierEntities();
        }
        return hotelierList;
    }

    public void setHotelierList(List<HotelierEntity> hotelierList) {
        this.hotelierList = hotelierList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(HotelierEntity hotelier, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
