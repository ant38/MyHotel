package com.myhotel.beans.web;

import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.SpecificityEntity;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.SpecificityService;
import com.myhotel.beans.service.security.SecurityWrapper;
import com.myhotel.beans.web.util.MessageFactory;

import java.io.Serializable;
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

@Named("specificityBean")
@ViewScoped
public class SpecificityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SpecificityBean.class.getName());
    
    private List<SpecificityEntity> specificityList;

    private SpecificityEntity specificity;
    
    @Inject
    private SpecificityService specificityService;
    
    @Inject
    private HotelService hotelService;
    
    private List<HotelEntity> allHotelsList;
    
    public void prepareNewSpecificity() {
        reset();
        this.specificity = new SpecificityEntity();
        // set any default values now, if you need
        // Example: this.specificity.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (specificity.getId() != null) {
                specificity = specificityService.update(specificity);
                message = "message_successfully_updated";
            } else {
                specificity = specificityService.save(specificity);
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
        
        specificityList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            specificityService.delete(specificity);
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
    
    public void onDialogOpen(SpecificityEntity specificity) {
        reset();
        this.specificity = specificity;
    }
    
    public void reset() {
        specificity = null;
        specificityList = null;
        
        allHotelsList = null;
        
    }

    // Get a List of all hotel
    public List<HotelEntity> getHotels() {
        if (this.allHotelsList == null) {
            this.allHotelsList = hotelService.findAllHotelEntities();
        }
        return this.allHotelsList;
    }
    
    // Update hotel of the current specificity
    public void updateHotel(HotelEntity hotel) {
        this.specificity.setHotel(hotel);
        // Maybe we just created and assigned a new hotel. So reset the allHotelList.
        allHotelsList = null;
    }
    
    public SpecificityEntity getSpecificity() {
        if (this.specificity == null) {
            prepareNewSpecificity();
        }
        return this.specificity;
    }
    
    public void setSpecificity(SpecificityEntity specificity) {
        this.specificity = specificity;
    }
    
    public List<SpecificityEntity> getSpecificityList() {
        if (specificityList == null) {
            specificityList = specificityService.findAllSpecificityEntities();
        }
        return specificityList;
    }

    public void setSpecificityList(List<SpecificityEntity> specificityList) {
        this.specificityList = specificityList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(SpecificityEntity specificity, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
