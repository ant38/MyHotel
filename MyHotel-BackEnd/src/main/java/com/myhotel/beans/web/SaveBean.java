package com.myhotel.beans.web;

import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.SaveEntity;
import com.myhotel.beans.service.ClientService;
import com.myhotel.beans.service.OfferService;
import com.myhotel.beans.service.SaveService;
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

@Named("saveBean")
@ViewScoped
public class SaveBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SaveBean.class.getName());
    
    private List<SaveEntity> saveList;

    private SaveEntity save;
    
    @Inject
    private SaveService saveService;
    
    @Inject
    private ClientService clientService;
    
    @Inject
    private OfferService offerService;
    
    private DualListModel<OfferEntity> offers;
    private List<String> transferedOfferIDs;
    private List<String> removedOfferIDs;
    
    private List<ClientEntity> allClientsList;
    
    public void prepareNewSave() {
        reset();
        this.save = new SaveEntity();
        // set any default values now, if you need
        // Example: this.save.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (save.getId() != null) {
                save = saveService.update(save);
                message = "message_successfully_updated";
            } else {
                save = saveService.save(save);
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
        
        saveList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            saveService.delete(save);
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
    
    public void onDialogOpen(SaveEntity save) {
        reset();
        this.save = save;
    }
    
    public void reset() {
        save = null;
        saveList = null;
        
        offers = null;
        transferedOfferIDs = null;
        removedOfferIDs = null;
        
        allClientsList = null;
        
    }

    // Get a List of all client
    public List<ClientEntity> getClients() {
        if (this.allClientsList == null) {
            this.allClientsList = clientService.findAllClientEntities();
        }
        return this.allClientsList;
    }
    
    // Update client of the current save
    public void updateClient(ClientEntity client) {
        this.save.setClient(client);
        // Maybe we just created and assigned a new client. So reset the allClientList.
        allClientsList = null;
    }
    
    public DualListModel<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(DualListModel<OfferEntity> offers) {
        this.offers = offers;
    }
    
    public List<OfferEntity> getFullOffersList() {
        List<OfferEntity> allList = new ArrayList<>();
        allList.addAll(offers.getSource());
        allList.addAll(offers.getTarget());
        return allList;
    }
    
    public void onOffersDialog(SaveEntity save) {
        // Prepare the offer PickList
        this.save = save;
        List<OfferEntity> selectedOffersFromDB = offerService
                .findOffersBySave(this.save);
        List<OfferEntity> availableOffersFromDB = offerService
                .findAvailableOffers(this.save);
        this.offers = new DualListModel<>(availableOffersFromDB, selectedOffersFromDB);
        
        transferedOfferIDs = new ArrayList<>();
        removedOfferIDs = new ArrayList<>();
    }
    
    public void onOffersPickListTransfer(TransferEvent event) {
        // If a offer is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((OfferEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedOfferIDs.add(id);
                removedOfferIDs.remove(id);
            } else if (event.isRemove()) {
                removedOfferIDs.add(id);
                transferedOfferIDs.remove(id);
            }
        }
        
    }
    
    public void updateOffer(OfferEntity offer) {
        // If a new offer is created, we persist it to the database,
        // but we do not assign it to this save in the database, yet.
        offers.getTarget().add(offer);
        transferedOfferIDs.add(offer.getId().toString());
    }
    
    public void onOffersSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<OfferEntity> selectedOffersFromDB = offerService.findOffersBySave(this.save);
            List<OfferEntity> availableOffersFromDB = offerService.findAvailableOffers(this.save);

            // Because offers are lazily loaded, we need to fetch them now
            this.save = saveService.fetchOffers(this.save);
            
            for (OfferEntity offer : selectedOffersFromDB) {
                if (removedOfferIDs.contains(offer.getId().toString())) {
                    
                    this.save.getOffers().remove(offer);
                    
                }
            }
    
            for (OfferEntity offer : availableOffersFromDB) {
                if (transferedOfferIDs.contains(offer.getId().toString())) {
                    
                    this.save.getOffers().add(offer);
                    
                }
            }
            
            this.save = saveService.update(this.save);
            
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
    
    public SaveEntity getSave() {
        if (this.save == null) {
            prepareNewSave();
        }
        return this.save;
    }
    
    public void setSave(SaveEntity save) {
        this.save = save;
    }
    
    public List<SaveEntity> getSaveList() {
        if (saveList == null) {
            saveList = saveService.findAllSaveEntities();
        }
        return saveList;
    }

    public void setSaveList(List<SaveEntity> saveList) {
        this.saveList = saveList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(SaveEntity save, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
