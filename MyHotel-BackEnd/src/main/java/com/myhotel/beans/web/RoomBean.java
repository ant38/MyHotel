package com.myhotel.beans.web;

import com.myhotel.beans.domain.BookingEntity;
import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.OfferEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.domain.RoomType;
import com.myhotel.beans.service.BookingService;
import com.myhotel.beans.service.HotelService;
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
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@Named("roomBean")
@ViewScoped
public class RoomBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RoomBean.class.getName());
    
    private List<RoomEntity> roomList;

    private RoomEntity room;
    
    @Inject
    private RoomService roomService;
    
    @Inject
    private HotelService hotelService;
    
    @Inject
    private OfferService offerService;
    
    @Inject
    private BookingService bookingService;
    
    private DualListModel<OfferEntity> offers;
    private List<String> transferedOfferIDs;
    private List<String> removedOfferIDs;
    
    private DualListModel<BookingEntity> bookings;
    private List<String> transferedBookingIDs;
    private List<String> removedBookingIDs;
    
    private List<HotelEntity> allHotelsList;
    
    public void prepareNewRoom() {
        reset();
        this.room = new RoomEntity();
        // set any default values now, if you need
        // Example: this.room.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (room.getId() != null) {
                room = roomService.update(room);
                message = "message_successfully_updated";
            } else {
                room = roomService.save(room);
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
        
        roomList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            roomService.delete(room);
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
    
    public void onDialogOpen(RoomEntity room) {
        reset();
        this.room = room;
    }
    
    public void reset() {
        room = null;
        roomList = null;
        
        offers = null;
        transferedOfferIDs = null;
        removedOfferIDs = null;
        
        bookings = null;
        transferedBookingIDs = null;
        removedBookingIDs = null;
        
        allHotelsList = null;
        
    }

    // Get a List of all hotel
    public List<HotelEntity> getHotels() {
        if (this.allHotelsList == null) {
            this.allHotelsList = hotelService.findAllHotelEntities();
        }
        return this.allHotelsList;
    }
    
    // Update hotel of the current room
    public void updateHotel(HotelEntity hotel) {
        this.room.setHotel(hotel);
        // Maybe we just created and assigned a new hotel. So reset the allHotelList.
        allHotelsList = null;
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
    
    public void onOffersDialog(RoomEntity room) {
        // Prepare the offer PickList
        this.room = room;
        List<OfferEntity> selectedOffersFromDB = offerService
                .findOffersByRoom(this.room);
        List<OfferEntity> availableOffersFromDB = offerService
                .findAvailableOffers(this.room);
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
        // but we do not assign it to this room in the database, yet.
        offers.getTarget().add(offer);
        transferedOfferIDs.add(offer.getId().toString());
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
    
    public void onBookingsDialog(RoomEntity room) {
        // Prepare the booking PickList
        this.room = room;
        List<BookingEntity> selectedBookingsFromDB = bookingService
                .findBookingsByRoom(this.room);
        List<BookingEntity> availableBookingsFromDB = bookingService
                .findAvailableBookings(this.room);
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
        // but we do not assign it to this room in the database, yet.
        bookings.getTarget().add(booking);
        transferedBookingIDs.add(booking.getId().toString());
    }
    
    public void onOffersSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<OfferEntity> selectedOffersFromDB = offerService.findOffersByRoom(this.room);
            List<OfferEntity> availableOffersFromDB = offerService.findAvailableOffers(this.room);

            // Because offers are lazily loaded, we need to fetch them now
            this.room = roomService.fetchOffers(this.room);
            
            for (OfferEntity offer : selectedOffersFromDB) {
                if (removedOfferIDs.contains(offer.getId().toString())) {
                    
                    this.room.getOffers().remove(offer);
                    
                }
            }
    
            for (OfferEntity offer : availableOffersFromDB) {
                if (transferedOfferIDs.contains(offer.getId().toString())) {
                    
                    this.room.getOffers().add(offer);
                    
                }
            }
            
            this.room = roomService.update(this.room);
            
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
    
    public void onBookingsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<BookingEntity> selectedBookingsFromDB = bookingService.findBookingsByRoom(this.room);
            List<BookingEntity> availableBookingsFromDB = bookingService.findAvailableBookings(this.room);

            for (BookingEntity booking : selectedBookingsFromDB) {
                if (removedBookingIDs.contains(booking.getId().toString())) {
                    
                    // Because rooms are lazy loaded, we need to fetch them now
                    booking = bookingService.fetchRooms(booking);
                    booking.getRooms().remove(room);
                    bookingService.update(booking);
                    
                }
            }
    
            for (BookingEntity booking : availableBookingsFromDB) {
                if (transferedBookingIDs.contains(booking.getId().toString())) {
                    
                    // Because rooms are lazy loaded, we need to fetch them now
                    booking = bookingService.fetchRooms(booking);
                    booking.getRooms().add(room);
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
    
    public SelectItem[] getTypeSelectItems() {
        SelectItem[] items = new SelectItem[RoomType.values().length];

        int i = 0;
        for (RoomType type : RoomType.values()) {
            items[i++] = new SelectItem(type, getLabelForType(type));
        }
        return items;
    }
    
    public String getLabelForType(RoomType value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_room_type_" + value);
        return label == null? value.toString() : label;
    }
    
    public RoomEntity getRoom() {
        if (this.room == null) {
            prepareNewRoom();
        }
        return this.room;
    }
    
    public void setRoom(RoomEntity room) {
        this.room = room;
    }
    
    public List<RoomEntity> getRoomList() {
        if (roomList == null) {
            roomList = roomService.findAllRoomEntities();
        }
        return roomList;
    }

    public void setRoomList(List<RoomEntity> roomList) {
        this.roomList = roomList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(RoomEntity room, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
